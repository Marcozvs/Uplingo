import { Injectable, inject } from '@angular/core';
import { Router } from '@angular/router';

import { Actions, createEffect, ofType } from '@ngrx/effects';
import {
  catchError,
  EMPTY,
  from,
  map,
  mergeMap,
  of,
  switchMap,
  tap,
  delay,
} from 'rxjs';
import { OAuthService } from 'angular-oauth2-oidc';

import { userActions } from '@modules/user/store/user.actions';
import { IUser } from '@modules/user/interfaces/user.interfaces';
import { UserService } from '@modules/user/services/user.service';
import { storyActions } from '@modules/story/store/story.actions';
import { courseActions } from '@modules/course/store/course.actions';

@Injectable({ providedIn: 'root' })
export class UserEffects {
  private readonly actions$: Actions = inject(Actions);
  private readonly router: Router = inject(Router);
  private readonly oAuthService: OAuthService = inject(OAuthService);
  private readonly userService: UserService = inject(UserService);

  logIn$ = createEffect(() =>
    this.actions$.pipe(
      ofType(userActions.logIn),
      switchMap(() => {
        const hasValidTokens =
          this.oAuthService.hasValidIdToken() &&
          this.oAuthService.hasValidAccessToken();

        if (hasValidTokens) {
          return of(userActions.logInSuccess());
        }

        return from(this.oAuthService.loadDiscoveryDocument()).pipe(
          tap((discoveryDocumentLoaded) => {
            if (discoveryDocumentLoaded) {
              this.clearAuthStorage();
              this.oAuthService.initCodeFlow();
            } else {
            }
          }),
          mergeMap(() => EMPTY),
          catchError((error) => {
            this.clearAuthStorage();
            return of(userActions.logInError());
          })
        );
      })
    )
  );

  logInSuccess$ = createEffect(() =>
    this.actions$.pipe(
      ofType(userActions.logInSuccess),
      switchMap(() => {
        return of(null).pipe(
          delay(100),
          switchMap(() => {
            const claims = this.oAuthService.getIdentityClaims();
            const email = claims?.['sub'] || claims?.['email'];

            if (!email) {
              return of(userActions.logInError());
            }

            return this.userService.read(email).pipe(
              tap((user: IUser) => {
                if (user.firstTime) {
                  this.router.navigate(['/welcome']);
                }
              }),
              switchMap((user) => {
                return [
                  userActions.setData({
                    data: { user, loggedIn: true, logInRequestHandled: true },
                  }),
                  storyActions.loadStories(),
                  courseActions.loadCourses(),
                  courseActions.loadGrammarModules(),
                ];
              }),
              catchError(() => {
                return of(userActions.logInError());
              })
            );
          })
        );
      })
    )
  );

  logInError$ = createEffect(
    () =>
      this.actions$.pipe(
        ofType(userActions.logInError),
        tap(() => {
          this.clearAuthStorage();
          setTimeout(() => {
            this.oAuthService.initCodeFlow();
          }, 1000);
        })
      ),
    { dispatch: false }
  );

  loadOAuthConfig$ = createEffect(() =>
    this.actions$.pipe(
      ofType(userActions.setOAuthConfig),
      switchMap((action) => {
        const config = action.config;
        this.oAuthService.configure(config);

        return from(this.oAuthService.loadDiscoveryDocument()).pipe(
          delay(100),
          map(() => {
            const hasAuthCode = window.location.search.includes('code=');
            const loggedIn =
              this.oAuthService.hasValidIdToken() &&
              this.oAuthService.hasValidAccessToken();

            if (loggedIn) {
              return userActions.logInSuccess();
            }

            if (hasAuthCode) {
              return userActions.processAuthCode();
            }

            if (!window.location.pathname.includes('/login/callback')) {
              return userActions.logIn();
            }

            return userActions.logInError();
          }),
          catchError((error) => {
            return of(userActions.logInError());
          })
        );
      })
    )
  );

  processAuthCode$ = createEffect(() =>
    this.actions$.pipe(
      ofType(userActions.processAuthCode),
      switchMap(() => {
        return from(this.oAuthService.tryLogin()).pipe(
          delay(200),
          map((result) => {
            const hasValidTokens =
              this.oAuthService.hasValidIdToken() &&
              this.oAuthService.hasValidAccessToken();

            if (hasValidTokens) {
              window.history.replaceState({}, document.title, window.location.pathname);
              return userActions.logInSuccess();
            } else {
              return userActions.logInError();
            }
          }),
          catchError((error) => {
            return of(userActions.logInError());
          })
        );
      })
    )
  );

  private clearAuthStorage(): void {
    const keysToRemove = [
      'access_token',
      'id_token',
      'refresh_token',
      'nonce',
      'PKCE_verifier',
      'auth-state'
    ];

    keysToRemove.forEach(key => {
      localStorage.removeItem(key);
      sessionStorage.removeItem(key);
    });
  }
}
