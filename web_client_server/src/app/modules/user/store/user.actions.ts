import { AuthConfig } from 'angular-oauth2-oidc';
import { createActionGroup, emptyProps, props } from '@ngrx/store';

import { UserState } from '@modules/user/store/user.interface';

export const userActions = createActionGroup({
  source: 'User',
  events: {
    setData: props<{ data: Partial<UserState> }>(),
    logIn: emptyProps(),
    logInSuccess: emptyProps(),
    logInError: emptyProps(),
    setOAuthConfig: props<{ config: AuthConfig }>(),
    processAuthCode: emptyProps(),
  },
});
