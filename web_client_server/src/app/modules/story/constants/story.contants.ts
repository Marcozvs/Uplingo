import { GenreEnum, StoryStatusEnum } from "@modules/story/enums/story.enums";
import { IOption } from "@shared/interfaces/option.interfaces";

export const STORY_STATUS_OPTIONS: IOption<StoryStatusEnum>[] = [
  { value: StoryStatusEnum.CREATED, label: 'OPTION.STORY_STATUS.CREATED' },
  { value: StoryStatusEnum.IN_PROGRESS, label: 'OPTION.STORY_STATUS.IN_PROGRESS' },
  { value: StoryStatusEnum.COMPLETED, label: 'OPTION.STORY_STATUS.COMPLETED' },
];

export const GENRE_OPTIONS: IOption<GenreEnum>[] = [
  { value: GenreEnum.FANTASY, label: 'OPTION.GENRE.FANTASY' },
  { value: GenreEnum.SCI_FI, label: 'OPTION.GENRE.SCI_FI' },
  { value: GenreEnum.ROMANCE, label: 'OPTION.GENRE.ROMANCE' },
  { value: GenreEnum.MYSTERY, label: 'OPTION.GENRE.MYSTERY' },
  { value: GenreEnum.THRILLER, label: 'OPTION.GENRE.THRILLER' },
  { value: GenreEnum.HORROR, label: 'OPTION.GENRE.HORROR' },
  { value: GenreEnum.ADVENTURE, label: 'OPTION.GENRE.ADVENTURE' },
  { value: GenreEnum.HISTORICAL, label: 'OPTION.GENRE.HISTORICAL' },
  { value: GenreEnum.DRAMA, label: 'OPTION.GENRE.DRAMA' },
  { value: GenreEnum.COMEDY, label: 'OPTION.GENRE.COMEDY' },
  { value: GenreEnum.ACTION, label: 'OPTION.GENRE.ACTION' },
  { value: GenreEnum.CRIME, label: 'OPTION.GENRE.CRIME' },
  { value: GenreEnum.DETECTIVE, label: 'OPTION.GENRE.DETECTIVE' },
  { value: GenreEnum.BIOGRAPHY, label: 'OPTION.GENRE.BIOGRAPHY' },
  { value: GenreEnum.DYSTOPIAN, label: 'OPTION.GENRE.DYSTOPIAN' },
  { value: GenreEnum.PARANORMAL, label: 'OPTION.GENRE.PARANORMAL' },
  { value: GenreEnum.SUPERHERO, label: 'OPTION.GENRE.SUPERHERO' },
  { value: GenreEnum.FAIRY_TALE, label: 'OPTION.GENRE.FAIRY_TALE' },
  { value: GenreEnum.WESTERN, label: 'OPTION.GENRE.WESTERN' },
  { value: GenreEnum.SPOOKY, label: 'OPTION.GENRE.SPOOKY' },
];

