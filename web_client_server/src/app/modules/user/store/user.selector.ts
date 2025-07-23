import { userFeature } from "@modules/user/store/user.reducer";

export const {
  selectUser,
  selectLoggedIn,
  selectLogInRequestHandled,
} = userFeature;

export const userSelector = {
  selectUser,
  selectLoggedIn,
  selectLogInRequestHandled,
};
