import { IUser } from '@modules/user/interfaces/user.interfaces';

export interface UserState {
  user: IUser | undefined;
  loggedIn: boolean;
  logInRequestHandled: boolean;
}
