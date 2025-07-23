import { GenreEnum, StoryStatusEnum } from "@modules/story/enums/story.enums";
import { IChapterItem, IUserChapterResult } from "@modules/story/interfaces/chapter.interfaces";
import { IStoryCharacterItem } from "@modules/story/interfaces/story-character.interfaces";

export interface IStoryCreate {
  grammarModuleId: string;
}

export interface IStoryUpdate {
  id: string;
  userId: string;
  grammarModuleId: string;
  title: string;
  backOver: string;
  storyStatus: StoryStatusEnum;
  genre: GenreEnum;
  characterName?: string;
  characterDescription?: string;
  updatedBy: string;
}

export interface IStory {
  id: string;
  userId: string;
  grammarModuleId: string;
  title: string;
  backOver: string;
  storyStatus: StoryStatusEnum;
  genre: GenreEnum;
  createdAt: string;
  characters: IStoryCharacterItem[];
  chapters: IChapterItem[];
}

export interface IStoryItem {
  id: string;
  userId: string;
  grammarModuleId: string;
  title: string;
  storyStatus: StoryStatusEnum;
  genre?: GenreEnum;
}

export interface IStoryCheckAnswer {
  correct: boolean;
  result: IUserChapterResult;
  currentDialogIndex: number;
}
