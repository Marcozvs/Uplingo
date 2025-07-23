import { ICourse, ICourseItem } from '@modules/course/interfaces/course.interfaces';
import { IGrammarModule, IGrammarModuleItem } from '@modules/course/interfaces/grammar-module.interfaces';

export interface CourseState {
  courses: ICourseItem[] | undefined;
  grammarModules: IGrammarModuleItem[] | undefined;
}
