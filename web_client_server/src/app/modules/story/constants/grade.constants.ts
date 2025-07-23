import { GradeEnum } from "@modules/story/enums/grade.enums";
import { IOption } from "@shared/interfaces/option.interfaces";

export const GRADE_OPTIONS: IOption<GradeEnum>[] = [
  { value: GradeEnum.A, label: 'OPTION.GRADE.A' },
  { value: GradeEnum.B, label: 'OPTION.GRADE.B' },
  { value: GradeEnum.C, label: 'OPTION.GRADE.C' },
  { value: GradeEnum.D, label: 'OPTION.GRADE.D' },
  { value: GradeEnum.F, label: 'OPTION.GRADE.F' },
];
