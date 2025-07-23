import { Injectable, inject } from '@angular/core';

import { Actions, createEffect, ofType } from '@ngrx/effects';
import { catchError, map, of, switchMap } from 'rxjs';

import { storyActions } from '@modules/story/store/story.actions';
import { StoryService } from '@modules/story/services/story.service';

@Injectable()
export class StoryEffects {
  private readonly actions$ = inject(Actions);
  private readonly storyService = inject(StoryService);

  readonly loadStories$ = createEffect(() =>
    this.actions$.pipe(
      ofType(storyActions.loadStories),
      switchMap(() =>
        this.storyService.readAll().pipe(
          map(stories => storyActions.setData({ data: { stories } })),
          catchError(() => of(storyActions.setData({ data: { stories: [] } })))
        )
      )
    )
  );
}
