import { CommonModule } from '@angular/common';
import { Component, DestroyRef, OnInit, inject } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';

import { TranslateModule } from '@ngx-translate/core';
import { take } from 'rxjs';

import { IStoryItem } from '@modules/story/interfaces/story.interfaces';
import { StoryFacade } from '@modules/story/store/story.facade';
import { StoryDinamicComponent } from '@modules/story/components/story-dinamic/story-dinamic.component';
import { StoryFormComponent } from '@modules/story/components/story-form/story-form.component';
import { StoryStatusEnum } from '@modules/story/enums/story.enums';

@Component({
  standalone: true,
  selector: 'app-story',
  imports: [CommonModule, StoryDinamicComponent, StoryFormComponent, TranslateModule],
  templateUrl: './story-page.component.html'
})
export class StoryPageComponent implements OnInit {
  private readonly route: ActivatedRoute = inject(ActivatedRoute);
  private readonly router: Router = inject(Router);
  private readonly storyFacade: StoryFacade = inject(StoryFacade);
  private readonly destroyRef: DestroyRef = inject(DestroyRef);

  storyId: string | undefined;

  ngOnInit(): void {
    this.route.paramMap
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe(params => {
        const id = params.get('id');
        if (id) {
          this.storyId = id;
        } else {
          this.storyFacade.stories$.pipe(take(1)).subscribe({
            next: (stories: IStoryItem[] | undefined) => {
              if (stories && stories.length > 0) {
                if (stories[stories.length - 1].storyStatus === StoryStatusEnum.IN_PROGRESS) {
                  this.router.navigate([`/story/${stories[stories.length - 1].id}`]);
                } else {
                  this.router.navigate(['/story']);
                }
              }
            }
          });
        }
      });
  }

}
