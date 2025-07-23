import { createActionGroup, emptyProps, props } from '@ngrx/store';

import { CourseState } from '@modules/course/store/course.interface';

export const courseActions = createActionGroup({
  source: 'course',
  events: {
    setData: props<{ data: Partial<CourseState> }>(),
    loadCourses: emptyProps(),
    loadGrammarModules: emptyProps(),
  },
});
