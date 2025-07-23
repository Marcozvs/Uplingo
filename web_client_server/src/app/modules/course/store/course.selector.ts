import { courseFeature } from "@modules/course/store/course.reducer";

export const {
  selectCourses,
  selectGrammarModules,
} = courseFeature;

export const courseSelector = {
  selectCourses,
  selectGrammarModules,
};
