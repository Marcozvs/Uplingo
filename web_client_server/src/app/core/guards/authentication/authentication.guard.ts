import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';

import { map, of, switchMap, take, distinctUntilChanged, filter, timeout, startWith } from 'rxjs';

import { IUser } from '@modules/user/interfaces/user.interfaces';
import { UserFacade } from '@modules/user/store/user.facade';

export const authenticationGuard: CanActivateFn = (route, state) => {
  const userFacade: UserFacade = inject(UserFacade);
  const router: Router = inject(Router);

  if (route.routeConfig?.path === 'login/callback') {
    return true;
  }

  if (route.routeConfig?.path === '' || route.routeConfig?.path === '**') {
    return true;
  }

  return of(null).pipe(
    switchMap(() => userFacade.logInRequestHandled$.pipe(
      startWith(undefined),
      distinctUntilChanged(),
      take(1),
      timeout(10000),
      switchMap((handled) => {
        if (handled === false || handled === undefined) {
          return userFacade.logInRequestHandled$.pipe(
            filter(h => h === true),
            take(1),
            timeout(8000),
            switchMap(() => checkUserAuthentication(userFacade, router, state))
          );
        }

        return checkUserAuthentication(userFacade, router, state);
      })
    ))
  );

  function checkUserAuthentication(userFacade: UserFacade, router: Router, state: any) {
    return userFacade.loggedIn$.pipe(
      take(1),
      switchMap((loggedIn) => {
        if (!loggedIn) {
          router.navigate(['/login']);
          return of(false);
        }

        return userFacade.user$.pipe(
          filter((user): user is IUser => !!user),
          take(1),
          map((user) => {
            const isWelcomeRoute = state.url.startsWith('/welcome');
            if (user.firstTime && !isWelcomeRoute) {
              router.navigate(['/welcome']);
              return false;
            }
            return true;
          })
        );
      })
    );
  }

};
