import { Injectable, inject } from '@angular/core';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';

import { userSelector } from './user.selector';
import { UserState } from './user.interface';
import { userActions } from './user.actions';
import { IUser } from '@modules/user/interfaces/user.interfaces';

@Injectable({
  providedIn: 'root',
})
export class UserFacade {
  private readonly store: Store = inject(Store);

  readonly user$: Observable<IUser | undefined> = this.store.select(
    userSelector.selectUser
  );

  readonly loggedIn$: Observable<boolean | undefined> = this.store.select(
    userSelector.selectLoggedIn
  );

  readonly logInRequestHandled$: Observable<boolean | undefined> =
    this.store.select(userSelector.selectLogInRequestHandled);

  setUserData(data: Partial<UserState>): void {
    this.store.dispatch(userActions.setData({ data }));
  }

  logIn(): void {
    this.store.dispatch(userActions.logIn());
  }

  logInSuccess(): void {
    this.store.dispatch(userActions.logInSuccess());
  }

  logInError(): void {
    this.store.dispatch(userActions.logInError());
  }
}
