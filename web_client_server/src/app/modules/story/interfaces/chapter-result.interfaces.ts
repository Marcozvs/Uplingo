import { GradeEnum } from "@modules/story/enums/grade.enums";

export interface IChapterResultCreate {
  id: string;
  userId: string;
  chapterId: string;
  grade: GradeEnum;
  xpEarned: number;
  completedAt: string;
}

export interface IChapterResultUpdate {
  id: string;
  userId: string;
  chapterId: string;
  grade: GradeEnum;
  xpEarned: number;
  completedAt: string;
  updatedBy: string;
}

export interface IChapterResult {
  id: string;
  userId: string;
  chapterId: string;
  grade: GradeEnum;
  xpEarned: number;
  completedAt: string;
  updatedBy: string;
  createdAt: string;
}

export interface IChapterResultItem {
  id: string;
  userId: string;
  chapterId: string;
  grade: GradeEnum;
  xpEarned: number;
  completedAt: string;
}
