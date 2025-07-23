import { Component, HostListener, OnInit, inject, DestroyRef } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';

import { TranslateModule } from '@ngx-translate/core';
import { filter, take } from 'rxjs';

import { IUser } from '@modules/user/interfaces/user.interfaces';
import { UserFacade } from '@modules/user/store/user.facade';
import { StoryFacade } from '@modules/story/store/story.facade';
import { IStoryItem } from '@modules/story/interfaces/story.interfaces';
import { StoryStatusEnum } from '@modules/story/enums/story.enums';
import { LayoutFacade } from '@layout/store/layout.facade';
import { ISidebarOption } from '@layout/interfaces/sidebar.interfaces';
import { FIRST_SIDEBAR_OPTIONS, SECOND_SIDEBAR_OPTIONS } from '@layout/constants/sidebar.constants';
import { IconButtonComponent } from '@shared/components/icon-button/icon-button.component';
import { CardComponent } from '@shared/components/card/card.component';
import { UserService } from '@modules/user/services/user.service';
import { StoryService } from '@modules/story/services/story.service';

@Component({
  standalone: true,
  selector: 'app-sidebar',
  imports: [CommonModule, IconButtonComponent, CardComponent, TranslateModule],
  templateUrl: './sidebar.component.html',
})
export class SidebarComponent implements OnInit {
  private readonly router: Router = inject(Router);
  private readonly storyService: StoryService = inject(StoryService);
  private readonly layoutFacade: LayoutFacade = inject(LayoutFacade);
  private readonly userFacade: UserFacade = inject(UserFacade);
  private readonly storyFacade: StoryFacade = inject(StoryFacade);
  private readonly destroyRef: DestroyRef = inject(DestroyRef);

  isHovered: boolean = false;
  isMobileOrTablet: boolean = false;
  isSidebarOpen: boolean = true;
  firstOptions: ISidebarOption[] = [];
  secondOptions: ISidebarOption[] = [];
  stories: IStoryItem[] = [];
  storyStatus = StoryStatusEnum;
  openPopoverId: string | null = null;

  ngOnInit(): void {
    this.defineScreenSize();
    this.defineSidebarState();
  }

  @HostListener('window:resize')
  onResize(): void {
    this.defineScreenSize();
  }

  private defineScreenSize(): void {
    this.isMobileOrTablet = window.innerWidth < 1024;
  }

  private defineSidebarState(): void {
    this.layoutFacade.sidebar$
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe((sidebarOpen: boolean) => {
        this.isSidebarOpen = sidebarOpen;

        const savedSidebar = sessionStorage.getItem('sidebar');
        if (savedSidebar && savedSidebar !== 'undefined') {
          this.layoutFacade.setLayoutData({ sidebar: JSON.parse(savedSidebar) });
        }

        this.userFacade.user$
          .pipe(
            filter((user): user is IUser => !!user),
            takeUntilDestroyed(this.destroyRef)
          )
          .subscribe((user: IUser) => {
            this.firstOptions = FIRST_SIDEBAR_OPTIONS.filter(option =>
              option.roles.some(role => user.roles.includes(role))
            );
            this.secondOptions = SECOND_SIDEBAR_OPTIONS.filter(option =>
              option.roles.some(role => user.roles.includes(role))
            );

            this.defineStories();
          });
      });
  }

  private defineStories(): void {
    this.storyFacade.stories$
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe((stories: IStoryItem[] | undefined) => {
        this.stories = [...(stories ?? [])].reverse();

        if (this.stories.length > 0) {
          if (this.stories.some((story) => story.storyStatus !== StoryStatusEnum.COMPLETED)) {
            this.firstOptions = this.firstOptions.filter(op => op.id !== 1);
          } else {
            this.firstOptions = this.firstOptions.filter(op => op.id !== 2);
          }
        } else {
          this.firstOptions = this.firstOptions.filter(op => op.id !== 2);
        }
      });
  }

  handleButtonClick(event: Event): void {
    event.stopPropagation();
    this.layoutFacade.toggleSidebar();
  }

  handleChangeHover(state: boolean): void {
    this.isHovered = state;
  }

  handleButtonOption(event: Event, option: ISidebarOption): void {
    event.stopPropagation();

    if (option.id === 2) {
      const inProgressStoryId = this.stories.find(
        story =>
          story.storyStatus === StoryStatusEnum.IN_PROGRESS ||
          story.storyStatus === StoryStatusEnum.CREATED
      )?.id;

      if (inProgressStoryId) {
        this.router.navigate([`/story/${inProgressStoryId}`]);
        return;
      }
    }

    if (option.redirectUrl) {
      this.router.navigate([option.redirectUrl]);
    }
  }

  handleNavigateToStory(event: Event, storyId: string): void {
    event.stopPropagation();
    this.router.navigate([`/story/${storyId}`]);
  }

  handleOpenMenu(optionId: string): void {
    this.openPopoverId = this.openPopoverId === optionId ? null : optionId;
  }

  handleRemoveStory(storyId: string): void {
    this.storyService.delete(storyId).pipe(take(1)).subscribe({
      complete: () => {
        this.storyFacade.loadStories();
        this.router.navigate(['/story']);
      }
    });
  }

  @HostListener('document:click', ['$event'])
  onDocumentClick(event: Event): void {
    const target = event.target as HTMLElement;
    const clickedInsidePopover = target.closest('.relative');
    if (!clickedInsidePopover) {
      this.openPopoverId = null;
    }
  }

  getIsActiveOption(option: ISidebarOption): boolean {
    return option.redirectUrl ? this.router.url.includes(option.redirectUrl) : false;
  }

  getIsActiveStory(storyId: string): boolean {
    return this.router.url.includes(`/story/${storyId}`);
  }
}
