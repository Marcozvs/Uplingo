import { ChapterStatusEnum } from "@modules/story/enums/chapter.enums";
import { IChapterAnswerItem } from "@modules/story/interfaces/chapter-answer.interfaces";
import { IChapterNarrationItem } from "@modules/story/interfaces/chapter-narration.interfaces";
import { IChapterResult, IChapterResultItem } from "@modules/story/interfaces/chapter-result.interfaces";
import { GradeEnum } from "@modules/story/enums/grade.enums";

export interface IChapterCreate {
  userId: string;
  storyId: string;
}

export interface IChapterUpdate {
  id: string;
  userId: string;
  storyId: string;
  chapterIndex: number;
  currentDialogIndex: number;
  title: string;
  introduction: string;
  chapterStatus: ChapterStatusEnum;
  updatedBy: string;
}

export interface IChapter {
  id: string;
  userId: string;
  storyId: string;
  chapterIndex: number;
  currentDialogIndex: number;
  title: string;
  introduction: string;
  chapterStatus: ChapterStatusEnum;
  updatedBy: string;
  result: IChapterResult;
  narrations: IChapterNarrationItem[];
  answers: IChapterAnswerItem[];
}

export interface IChapterItem {
  id: string;
  userId: string;
  storyId: string;
  chapterIndex: number;
  currentDialogIndex: number;
  title: string;
  introduction: string;
  narrations: IChapterNarrationItem[];
  answers: IChapterAnswerItem[];
  results: IChapterResultItem[];
}

export interface IUserChapterResult {
  id: string;
  userId: string;
  chapterId: string;
  grade: GradeEnum;
  xpEarned: number;
  completedAt: string;
  createdBy: string;
  createdAt: string;
  updatedAt: string;
  updatedBy: string;
}
