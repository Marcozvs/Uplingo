import { ChapterStatusEnum } from '@modules/story/enums/chapter.enums';
import { IOption } from '@shared/interfaces/option.interfaces';

export const CHAPTER_STATUS_OPTIONS: IOption<ChapterStatusEnum>[] = [
  { value: ChapterStatusEnum.CREATED, label: 'OPTION.CHAPTER_STATUS.CREATED' },
  { value: ChapterStatusEnum.IN_PROGRESS, label: 'OPTION.CHAPTER_STATUS.IN_PROGRESS' },
  { value: ChapterStatusEnum.COMPLETED, label: 'OPTION.CHAPTER_STATUS.COMPLETED' },
];
