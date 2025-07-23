import { Injectable, inject } from '@angular/core';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';

import { storySelector } from '@modules/story/store/story.selector';
import { StoryState } from '@modules/story/store/story.interface';
import { storyActions } from '@modules/story/store/story.actions';
import { IStory, IStoryItem } from '@modules/story/interfaces/story.interfaces';

@Injectable({
  providedIn: 'root',
})
export class StoryFacade {
  private readonly store: Store = inject(Store);

  readonly stories$: Observable<IStoryItem[] | undefined> = this.store.select(
    storySelector.selectStories
  );

  setStoryData(data: Partial<StoryState>): void {
    this.store.dispatch(storyActions.setData({ data }));
  }

  loadStories(): void {
    this.store.dispatch(storyActions.loadStories());
  }
}
