import { RoleEnum } from "@modules/user/enums/user.enums";

export interface ISidebarOption {
  id: number,
  title: string,
  icon?: string,
  redirectUrl?: string,
  roles: RoleEnum[]
}
