import { createFeature, createReducer, on } from '@ngrx/store';

import { CourseState } from '@modules/course/store/course.interface';
import { courseActions } from '@modules/course/store/course.actions';

export const courseInitialState: CourseState = {
  courses: [],
  grammarModules: [],
};

export const courseFeature = createFeature({
  name: 'course',
  reducer: createReducer(
    courseInitialState,
    on(courseActions.setData, (state, { data }) => ({ ...state, ...data }))
  ),
});
