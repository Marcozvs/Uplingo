import { ActionReducerMap } from "@ngrx/store";

import { UserState } from "@modules/user/store/user.interface";
import { userFeature } from "@modules/user/store/user.reducer";
import { StoryState } from "@modules/story/store/story.interface";
import { storyFeature } from "@modules/story/store/story.reducer";
import { CourseState } from "@modules/course/store/course.interface";
import { courseFeature } from "@modules/course/store/course.reducer";
import { LayoutState } from "@layout/store/layout.interface";
import { layoutFeature } from "@layout/store/layout.reducer";

interface AppState {
  user: UserState;
  layout: LayoutState;
  story: StoryState;
  course: CourseState;
}

export const reducers: ActionReducerMap<AppState> = {
  user: userFeature.reducer,
  layout: layoutFeature.reducer,
  story: storyFeature.reducer,
  course: courseFeature.reducer,
};
