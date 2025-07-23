import { Injectable, inject } from '@angular/core';

import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';

import { ICourse, ICourseItem } from '@modules/course/interfaces/course.interfaces';
import { courseSelector } from '@modules/course/store/course.selector';
import { CourseState } from '@modules/course/store/course.interface';
import { courseActions } from '@modules/course/store/course.actions';
import { IGrammarModule, IGrammarModuleItem } from '@modules/course/interfaces/grammar-module.interfaces';

@Injectable({
  providedIn: 'root',
})
export class CourseFacade {
  private readonly store: Store = inject(Store);

  readonly courses$: Observable<ICourseItem[] | undefined> = this.store.select(
    courseSelector.selectCourses
  );

  readonly grammarModules$: Observable<IGrammarModuleItem[] | undefined> = this.store.select(
    courseSelector.selectGrammarModules
  );

  setCourseData(data: Partial<CourseState>): void {
    this.store.dispatch(courseActions.setData({ data }));
  }

  loadCourses(): void {
    this.store.dispatch(courseActions.loadCourses());
  }

  loadGrammarModules(): void {
    this.store.dispatch(courseActions.loadGrammarModules());
  }
}
