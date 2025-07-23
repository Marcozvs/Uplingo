import { CourseNameEnum } from "@modules/course/enums/course.enums";
import { IOption } from "@shared/interfaces/option.interfaces";

export const COURSE_NAME_OPTIONS: IOption<CourseNameEnum>[] = [
  { value: CourseNameEnum.ENGLISH_COURSE, label: 'OPTION.COURSE.ENGLISH' },
  { value: CourseNameEnum.SPANISH_COURSE, label: 'OPTION.COURSE.SPANISH' },
  { value: CourseNameEnum.FRENCH_COURSE, label: 'OPTION.COURSE.FRENCH' },
  { value: CourseNameEnum.PORTUGUESE_BR_COURSE, label: 'OPTION.COURSE.PORTUGUESE_BR' },
];
