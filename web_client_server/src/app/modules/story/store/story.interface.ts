import { IStory, IStoryItem } from '@modules/story/interfaces/story.interfaces';

export interface StoryState {
  stories: IStoryItem[] | undefined;
}
