import { RoleEnum } from "@modules/user/enums/user.enums";
import { ISidebarOption } from "@layout/interfaces/sidebar.interfaces";

export const FIRST_SIDEBAR_OPTIONS: ISidebarOption[] = [
  {
    id: 1,
    title: 'LAYOUT.SIDEBAR.ITEMS.NEW_STORY',
    icon: 'plus',
    redirectUrl: '/story',
    roles: [RoleEnum.STUDENT, RoleEnum.ADMIN]
  },
  {
    id: 2,
    title: 'LAYOUT.SIDEBAR.ITEMS.CONTINUE_STORY',
    icon: 'play',
    redirectUrl: '/story',
    roles: [RoleEnum.STUDENT, RoleEnum.ADMIN]
  },
  {
    id: 3,
    title: 'LAYOUT.SIDEBAR.ITEMS.USER_MANAGEMENT',
    icon: 'account-group',
    redirectUrl: '/user-management',
    roles: [RoleEnum.ADMIN]
  },
];

export const SECOND_SIDEBAR_OPTIONS: ISidebarOption[] = [
  {
    id: 1,
    title: 'LAYOUT.SIDEBAR.ITEMS.SETTINGS',
    icon: 'cog-outline',
    redirectUrl: '/settings',
    roles: [RoleEnum.STUDENT, RoleEnum.ADMIN]
  },
  {
    id: 2,
    title: 'LAYOUT.SIDEBAR.ITEMS.LOGOUT',
    icon: 'logout',
    redirectUrl: '/logout',
    roles: [RoleEnum.STUDENT, RoleEnum.ADMIN]
  },
];
