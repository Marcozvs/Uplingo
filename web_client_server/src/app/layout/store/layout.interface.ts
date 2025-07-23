import { LanguageEnum } from "@shared/enum/language.enums";
import { IToast } from "@shared/interfaces/toast.interfaces";

export interface LayoutState {
  darkMode: boolean;
  language: LanguageEnum;
  loading: boolean;
  toastQueue: IToast[];
  sidebar: boolean;
}
