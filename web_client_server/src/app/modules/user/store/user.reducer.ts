import { createFeature, createReducer, on } from '@ngrx/store';

import { UserState } from '@modules/user/store/user.interface';
import { userActions } from '@modules/user/store/user.actions';

export const userInitialState: UserState = {
  user: undefined,
  loggedIn: false,
  logInRequestHandled: false,
};

export const userFeature = createFeature({
  name: 'user',
  reducer: createReducer(
    userInitialState,
    on(userActions.setData, (state, { data }) => ({ ...state, ...data })),
    on(userActions.logIn, (state: UserState) => ({
      ...state,
      loggedIn: false,
      logInRequestHandled: false,
    })),
    on(userActions.logInSuccess, (state: UserState) => ({
      ...state,
      loggedIn: true,
      logInRequestHandled: true,
    })),
    on(userActions.logInError, (state: UserState) => ({
      ...state,
      loggedIn: false,
      logInRequestHandled: true,
    }))
  ),
});
