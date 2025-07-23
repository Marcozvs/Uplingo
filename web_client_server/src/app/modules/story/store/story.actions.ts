import { createActionGroup, emptyProps, props } from '@ngrx/store';

import { StoryState } from '@modules/story/store/story.interface';

export const storyActions = createActionGroup({
  source: 'Story',
  events: {
    setData: props<{ data: Partial<StoryState> }>(),
    loadStories: emptyProps(),
  },
});
