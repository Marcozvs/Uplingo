import { CommonModule } from '@angular/common';
import { Component, EventEmitter, inject, OnInit, Output } from '@angular/core';
import { TranslateModule, TranslateService } from '@ngx-translate/core';
import {
  BehaviorSubject,
  Observable,
  combineLatest,
  map,
  of,
  tap,
  startWith,
} from 'rxjs';

import { IGrammarModuleItem } from '@modules/course/interfaces/grammar-module.interfaces';
import { CourseFacade } from '@modules/course/store/course.facade';
import { COURSE_NAME_OPTIONS } from '@modules/course/constants/course.constants';
import { CourseNameEnum } from '@modules/course/enums/course.enums';
import { CardComponent } from '@shared/components/card/card.component';
import { TabComponent } from '@shared/components/tab/tab.component';
import { ITabItem } from '@shared/interfaces/tab.interfaces';
import { ConfirmationModalComponent } from '@shared/components/confirmation/confirmation.component';

@Component({
  selector: 'app-course-list',
  standalone: true,
  imports: [CommonModule, TabComponent, CardComponent, ConfirmationModalComponent, TranslateModule],
  templateUrl: './course-list.component.html',
})
export class CourseListComponent implements OnInit {
  @Output() grammarModuleSelectedEvent = new EventEmitter<string>();

  private readonly translateService = inject(TranslateService);
  private readonly courseFacade = inject(CourseFacade);

  tabs: ITabItem[] = [];
  private tabSelectedSubject = new BehaviorSubject<string | undefined>(undefined);

  grammarModules$: Observable<IGrammarModuleItem[] | undefined> = this.courseFacade.grammarModules$;
  filteredModules$: Observable<IGrammarModuleItem[]> = of([]);

  confirmationModalOpen = false;
  confirmationContent = '';
  selectedModuleIdForStory: string | null = null;

  set tabSelectedValue(value: string | undefined) {
    this.tabSelectedSubject.next(value);
  }

  get tabSelectedValue(): string | undefined {
    return this.tabSelectedSubject.value;
  }

  ngOnInit(): void {
    this.courseFacade.loadCourses();

    combineLatest([
      this.courseFacade.courses$,
      this.courseFacade.grammarModules$
    ])
      .pipe(
        tap(([courses]) => {
          if (!courses || courses.length === 0) return;

          this.tabs = courses.map(course => ({
            id: course.id,
            title: this.translateService.instant(
              COURSE_NAME_OPTIONS.find(op => op.value === course.name)?.label ?? ''
            ),
            disabled: false,
          }));

          const courseKey = sessionStorage.getItem('app-course') as CourseNameEnum | null;
          const courseEnum = courseKey ?? CourseNameEnum.ENGLISH_COURSE;
          const matchedTab = courses.find(c => c.name === courseEnum);
          this.tabSelectedValue = matchedTab?.id ?? this.tabs[0]?.id;
        }),
        startWith([[], []])
      )
      .subscribe();

    this.filteredModules$ = combineLatest([
      this.courseFacade.grammarModules$,
      this.tabSelectedSubject.asObservable()
    ]).pipe(
      map(([modules, selectedId]) =>
        modules?.filter(module => module.courseId === selectedId) ?? []
      )
    );
  }

  handleConfirmCreateStory(moduleId: string): void {
    this.selectedModuleIdForStory = moduleId;
    this.confirmationContent = this.translateService.instant('PAGES.STORY_FORM.CONFIRM_CREATE_STORY');
    this.confirmationModalOpen = true;
  }

  handleModalConfirmed(): void {
    this.confirmationModalOpen = false;
    if (this.selectedModuleIdForStory) {
      this.grammarModuleSelectedEvent.emit(this.selectedModuleIdForStory);
      this.selectedModuleIdForStory = null;
    }
  }

  handleModalCancelled(): void {
    this.confirmationModalOpen = false;
    this.selectedModuleIdForStory = null;
  }
}
