import { LevelEnum } from "@modules/course/enums/grammar-module.enums";

export interface IGrammarModule {
  id: string;
  courseId: string;
  name: string;
  description: string;
  level: LevelEnum;
  createdAt: string;
}

export interface IGrammarModuleItem {
  id: string;
  courseId: string;
  name: string;
  description: string;
}
