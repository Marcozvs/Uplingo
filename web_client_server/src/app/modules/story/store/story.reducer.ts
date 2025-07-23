import { createFeature, createReducer, on } from '@ngrx/store';

import { StoryState } from '@modules/story/store/story.interface';
import { storyActions } from '@modules/story/store/story.actions';

export const storyInitialState: StoryState = {
  stories: [],
};

export const storyFeature = createFeature({
  name: 'story',
  reducer: createReducer(
    storyInitialState,
    on(storyActions.setData, (state, { data }) => ({ ...state, ...data }))
  ),
});
