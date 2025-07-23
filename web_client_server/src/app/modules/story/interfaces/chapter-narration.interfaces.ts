export interface IChapterNarrationCreate {
  chapterId: string;
  step: number;
}

export interface IChapterNarrationUpdate {
  id: string;
  chapterId: string;
  step: number;
  content: string;
}

export interface IChapterNarration {
  id: string;
  chapterId: string;
  step: number;
  content: string;
  createdAt: string;
}

export interface IChapterNarrationItem {
  id: string;
  chapterId: string;
  step: number;
  content: string;
  createdAt: string;
}
