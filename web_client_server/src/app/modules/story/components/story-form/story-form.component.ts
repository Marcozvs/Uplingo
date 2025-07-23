import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';

import { TranslateModule, TranslateService } from '@ngx-translate/core';
import { map, Observable, switchMap, take } from 'rxjs';
import { v4 as uuidv4 } from 'uuid';

import { CourseListComponent } from '@modules/course/components/course-list/course-list.component';
import { IUser } from '@modules/user/interfaces/user.interfaces';
import { UserFacade } from '@modules/user/store/user.facade';
import { StoryService } from '@modules/story/services/story.service';
import { IStory, IStoryCreate } from '@modules/story/interfaces/story.interfaces';
import { EnergyService } from '@modules/story/services/energy.service';
import { SkeletonComponent } from '@shared/components/skeleton/skeleton.component';
import { ButtonComponent } from '@shared/components/button/button.component';
import { LayoutFacade } from '@layout/store/layout.facade';
import { ToastSeverityEnum } from '@shared/enum/toast.enums';

@Component({
  standalone: true,
  selector: 'app-story-form',
  imports: [
    CommonModule,
    CourseListComponent,
    SkeletonComponent,
    ButtonComponent,
    TranslateModule
  ],
  templateUrl: './story-form.component.html',
})
export class StoryFormComponent {
  private readonly userFacade: UserFacade = inject(UserFacade);
  private readonly layoutFacade: LayoutFacade = inject(LayoutFacade);
  private readonly storyService: StoryService = inject(StoryService);
  private readonly energyService: EnergyService = inject(EnergyService);
  private readonly translateService: TranslateService = inject(TranslateService);
  private readonly router: Router = inject(Router);

  grammarModuleSelected: string | undefined;
  showSkeleton: boolean = false;

  handleGrammarModuleSelected(grammarModuleId: string): void {
    this.grammarModuleSelected = grammarModuleId;

    this.userFacade.user$.pipe(
      take(1),
      switchMap(user => {
        if (!user) return [];
        this.showSkeleton = true;
        const dto: IStoryCreate = {
          grammarModuleId
        };
        return this.storyService.create(dto);
      })
    ).subscribe({
      next: (story: IStory | undefined) => {
        if (story) {
          this.router.navigate([`/story/${story.id}`]);
        }
      }
    });
  }

  handleRequestEnergy(): void {
    this.energyService.request().pipe(take(1)).subscribe({
      complete: () => {
        this.layoutFacade.addToast({
          id: uuidv4(),
          message: this.translateService.instant('TOAST.REQUEST_ENERGY.SENT'),
          severity: ToastSeverityEnum.SUCCESS,
          displayed: false,
        });
      },
    });
  }

  get getUserHasEnergy(): Observable<boolean> {
    return this.userFacade.user$.pipe(
      map((user: IUser | undefined): boolean => {
        return !!user?.energy && user.energy > 0;
      })
    )
  }
}
