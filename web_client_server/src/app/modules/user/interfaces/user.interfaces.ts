import { LanguageEnum } from "@shared/enum/language.enums";
import { IUserHobbyItem } from "@modules/user/interfaces/hobby.interfaces";
import { RoleEnum } from "../enums/user.enums";

export interface IUser {
  id: string;
  name: string;
  email: string;
  roles: RoleEnum[];
  description?: string;
  reasonsLearn?: string;
  userLanguage: LanguageEnum;
  photoUrl: string;
  banned: boolean;
  bannedAt?: string;
  energy: number;
  totalXP: number;
  firstTime: boolean;
  hobbies: IUserHobbyItem[];
}

export interface IUserItem {
  id: string;
  name: string;
  email: string;
  photoUrl: string;
  banned: boolean;
}

export interface IUserUpdate {
  id?: string;
  description?: string;
  reasonsLearn?: string;
  userLanguage?: LanguageEnum;
  firstTime?: boolean;
}

export interface IOAuthUserProfile {
  sub: string;
  name: string;
  email: string;
}

export interface ICreateUserEnergy {
  userId: string;
  validUntil: string;
}
