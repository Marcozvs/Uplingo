import { RoleEnum } from '@modules/user/enums/user.enums';
import { IOption } from '@shared/interfaces/option.interfaces';

export const ROLE_OPTIONS: IOption<RoleEnum>[] = [
  { value: RoleEnum.ADMIN, label: 'OPTION.ROLE.ADMIN' },
  { value: RoleEnum.STUDENT, label: 'OPTION.ROLE.STUDENT' }
];
