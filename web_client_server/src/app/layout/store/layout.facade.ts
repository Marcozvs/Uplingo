import { Injectable, inject } from '@angular/core';

import { Store } from '@ngrx/store';

import { Observable } from 'rxjs';

import { layoutSelector } from '@layout/store/layout.selector';
import { LayoutState } from '@layout/store/layout.interface';
import { layoutActions } from '@layout/store/layout.actions';
import { IToast } from '@shared/interfaces/toast.interfaces';
import { LanguageEnum } from '@shared/enum/language.enums';
import { getLanguageCode } from '@shared/constants/language.constants';

@Injectable({
  providedIn: 'root',
})
export class LayoutFacade {
  private readonly store: Store = inject(Store);

  readonly darkMode$: Observable<boolean> = this.store.select(
    layoutSelector.selectDarkMode
  );

  readonly loading$: Observable<boolean> = this.store.select(
    layoutSelector.selectLoading
  );

  readonly layoutState$: Observable<LayoutState> = this.store.select(
    layoutSelector.selectLayoutState
  );

  readonly toastQueue$: Observable<IToast[]> = this.store.select(
    layoutSelector.selectToastQueue
  );

  readonly sidebar$: Observable<boolean> = this.store.select(
    layoutSelector.selectSidebar
  );

  setLayoutData(data: Partial<LayoutState>): void {
    this.store.dispatch(layoutActions.setData({ data }));
  }

  enableDarkMode(): void {
    this.setLayoutData({ darkMode: true });
  }

  disableDarkMode(): void {
    this.setLayoutData({ darkMode: false });
  }

  toggleDarkMode(): void {
    this.store.dispatch(layoutActions.toggleTheme());
  }

  addToast(toast: IToast): void {
    this.store.dispatch(layoutActions.addToast({ toast }));
  }

  toggleSidebar(): void {
    this.store.dispatch(layoutActions.sidebar());
  }

  setLanguage(language: LanguageEnum): void {
    sessionStorage.setItem('app-language', getLanguageCode(language));
    this.setLayoutData({ language });
  }
}
