import { CharacterTypeEnum } from "@modules/story/enums/story.enums";

export interface IChapterAnswerCreate {
  chapterId: string;
  nextStep: number;
}

export interface IChapterAnswerUpdate {
  chapterId: string;
  content: string;
  correct: boolean;
  step: number;
  updatedBy: string;
}

export interface IChapterAnswer {
  id: string;
  chapterId: string;
  characterId: string;
  characterType: CharacterTypeEnum;
  characterName: string;
  answerIndex: number;
  content: string;
  step: number;
  correct: boolean;
  createdAt: string;
}

export interface IChapterAnswerItem {
  id: string;
  chapterId: string;
  characterId: string;
  characterType: CharacterTypeEnum;
  characterName: string;
  answerIndex: number;
  step: number;
  content: string;
  correct: boolean;
}
