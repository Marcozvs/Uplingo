import { CourseNameEnum } from "@modules/course/enums/course.enums";
import { IGrammarModuleItem } from "@modules/course/interfaces/grammar-module.interfaces";

export interface ICourse {
  id: string;
  name: CourseNameEnum;
  description: string;
  createdAt: string;
  modules: IGrammarModuleItem[];
}

export interface ICourseItem {
  id: string;
  name: CourseNameEnum;
  description: string;
}
