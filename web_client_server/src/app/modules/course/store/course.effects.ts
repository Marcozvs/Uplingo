import { Injectable, inject } from '@angular/core';

import { Actions, createEffect, ofType } from '@ngrx/effects';
import { catchError, map, of, switchMap } from 'rxjs';

import { CourseService } from '@modules/course/services/course.service';
import { courseActions } from '@modules/course/store/course.actions';
import { GrammarModuleService } from '../services/grammar-module.service';

@Injectable()
export class CourseEffects {
  private readonly actions$ = inject(Actions);
  private readonly courseService = inject(CourseService);
  private readonly grammarModuleService = inject(GrammarModuleService);

  readonly loadCoursesAndModules$ = createEffect(() =>
    this.actions$.pipe(
      ofType(courseActions.loadCourses),
      switchMap(() =>
        this.courseService.readAll().pipe(
          switchMap(courses =>
            this.grammarModuleService.readAll().pipe(
              map(modules =>
                courseActions.setData({
                  data: {
                    courses,
                    grammarModules: modules,
                  },
                })
              ),
              catchError(() =>
                of(
                  courseActions.setData({
                    data: { courses, grammarModules: [] },
                  })
                )
              )
            )
          ),
          catchError(() =>
            of(
              courseActions.setData({
                data: { courses: [], grammarModules: [] },
              })
            )
          )
        )
      )
    )
  );
}
