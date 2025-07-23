import { LevelEnum } from "@modules/course/enums/grammar-module.enums";
import { IOption } from "@shared/interfaces/option.interfaces";

export const LEVEL_OPTIONS: IOption<LevelEnum>[] = [
  { value: LevelEnum.A1, label: 'OPTION.LEVEL.A1' },
  { value: LevelEnum.A2, label: 'OPTION.LEVEL.A2' },
  { value: LevelEnum.B1, label: 'OPTION.LEVEL.B1' },
  { value: LevelEnum.B2, label: 'OPTION.LEVEL.B2' },
  { value: LevelEnum.C1, label: 'OPTION.LEVEL.C1' },
  { value: LevelEnum.C2, label: 'OPTION.LEVEL.C2' },
];
