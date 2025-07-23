import { ToastSeverityEnum } from "@shared/enum/toast.enums";

export interface IToast {
  id: string;
  title?: string;
  message: string;
  severity: ToastSeverityEnum;
  duration?: number;
  displayed: boolean;
}

export type IRegisterToast = Omit<IToast, 'id' | 'displayed'>;
