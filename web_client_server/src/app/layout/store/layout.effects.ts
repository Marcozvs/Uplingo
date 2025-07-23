import { inject, Injectable } from '@angular/core';

import { Actions, createEffect, ofType } from '@ngrx/effects';
import { Store } from '@ngrx/store';

import { of } from 'rxjs';
import { delay, map, mergeMap, take, tap, withLatestFrom } from 'rxjs/operators';

import { LayoutService } from '@layout/services/layout.service';
import { layoutActions } from '@layout/store/layout.actions';
import { layoutSelector } from '@layout/store/layout.selector';
import { getLanguageCode } from '@shared/constants/language.constants';

@Injectable()
export class LayoutEffects {
  private readonly layoutService = inject(LayoutService);
  private readonly actions$ = inject(Actions);
  private readonly store = inject(Store);

  toggleThemeEffect$ = createEffect(
    () =>
      this.actions$.pipe(
        ofType(layoutActions.toggleTheme),
        tap(() => {
          this.store
            .select(layoutSelector.selectDarkMode)
            .pipe(take(1))
            .subscribe((isDark) => {
              const theme = isDark ? 'dark' : 'light';
              this.layoutService.applyTheme(theme);
            });
        })
      ),
    { dispatch: false }
  );

  removeToastEffect$ = createEffect(() =>
    this.actions$.pipe(
      ofType(layoutActions.addToast),
      mergeMap(action =>
        of(action.toast).pipe(
          delay(action.toast.duration || 3000),
          map(toast => layoutActions.removeToast({ toast }))
        )
      )
    )
  );

  sidebarEffect$ = createEffect(
    () =>
      this.actions$.pipe(
        ofType(layoutActions.sidebar),
        withLatestFrom(this.store.select(layoutSelector.selectSidebar)),
        tap(([_, sidebar]) => {
          sessionStorage.setItem('sidebar', JSON.stringify(sidebar));
        })
      ),
    { dispatch: false }
  );

  languageEffect$ = createEffect(
    () =>
      this.actions$.pipe(
        ofType(layoutActions.setData),
        withLatestFrom(this.store.select(layoutSelector.selectLanguage)),
        tap(([action, lang]) => {
          if (action.data.language !== undefined) {
            const code = getLanguageCode(action.data.language);
            sessionStorage.setItem('app-language', code);
          }
        })
      ),
    { dispatch: false }
  );
}
