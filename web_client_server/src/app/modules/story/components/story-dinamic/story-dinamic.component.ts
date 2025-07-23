import { CommonModule } from '@angular/common';
import { Component, inject, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';

import { TranslateModule } from '@ngx-translate/core';
import { take } from 'rxjs';

import { IStory, IStoryCheckAnswer } from '@modules/story/interfaces/story.interfaces';
import { StoryService } from '@modules/story/services/story.service';
import { GENRE_OPTIONS, STORY_STATUS_OPTIONS } from '@modules/story/constants/story.contants';
import { ICourseItem } from '@modules/course/interfaces/course.interfaces';
import { CourseFacade } from '@modules/course/store/course.facade';
import { IGrammarModuleItem } from '@modules/course/interfaces/grammar-module.interfaces';
import { COURSE_NAME_OPTIONS } from '@modules/course/constants/course.constants';
import { IChapterNarrationItem } from '@modules/story/interfaces/chapter-narration.interfaces';
import { IChapterAnswerItem } from '@modules/story/interfaces/chapter-answer.interfaces';
import { CardComponent } from '@shared/components/card/card.component';
import { CharacterTypeEnum } from '@modules/story/enums/story.enums';
import { SkeletonComponent } from '@shared/components/skeleton/skeleton.component';

interface IStepGroup {
  step: number;
  narration: IChapterNarrationItem | null;
  answers: IChapterAnswerItem[];
}

@Component({
  standalone: true,
  selector: 'app-story-dinamic',
  imports: [CommonModule, CardComponent, SkeletonComponent, TranslateModule],
  templateUrl: './story-dinamic.component.html',
})
export class StoryDinamicComponent implements OnInit, OnChanges {
  @Input({ required: true }) storyId: string | undefined;

  private storyService: StoryService = inject(StoryService);
  private courseFacade: CourseFacade = inject(CourseFacade);

  showSkeleton: boolean = true;
  story: IStory | undefined;
  grammarModuleItem: IGrammarModuleItem | undefined;
  courseItem: ICourseItem | undefined;
  currentStep: number = 1;
  currentDialogIndex: number = 1;
  answerCheckedId: string | undefined;
  correctAnswer: boolean | undefined;

  ngOnInit(): void {
    this.defineStory();
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['storyId']?.currentValue) {
      this.defineStory();
    }
  }

  private defineStory(): void {
    this.showSkeleton = true;
    if (this.storyId)
      this.storyService.read(this.storyId).pipe(take(1)).pipe(take(1)).subscribe({
        next: (story: IStory | undefined) => {
          this.story = story;
        },
        complete: () => {
          this.showSkeleton = false;
          this.defineGrammarModule();
        }
      })
  }

  private defineGrammarModule(): void {
    this.courseFacade.grammarModules$.pipe(take(1)).subscribe({
      next: (grammarModules: IGrammarModuleItem[] | undefined) => {
        if (grammarModules && grammarModules.length > 0)
          this.grammarModuleItem =
            grammarModules.find(grammarModule => grammarModule.id === this.story?.grammarModuleId);
      },
      complete: () => {
        this.defineCourse();
      }
    });
  }

  private defineCourse(): void {
    this.courseFacade.courses$.pipe(take(1)).subscribe({
      next: (courses: ICourseItem[] | undefined) => {
        if (courses && courses.length > 0)
          this.courseItem =
            courses.find(course => course.id === this.grammarModuleItem?.courseId);
      },
      complete: () => {
        this.defineSteps();
      }
    })
  }

  private defineSteps(): void {
    if (this.story)
      this.currentDialogIndex = this.story.chapters[this.story.chapters.length - 1]?.currentDialogIndex;

  }

  handleCheckAnswer(answer: IChapterAnswerItem): void {
    if (this.answerCheckedId === undefined && !this.getIsSupportingCharacter(answer.characterType)) {
      this.answerCheckedId = answer.id;
      this.storyService.checkAnswer(answer.id).pipe(take(1)).subscribe({
        next: (avalation: IStoryCheckAnswer) => {
          this.correctAnswer = avalation.correct;
          setTimeout(() => {
            this.answerCheckedId = undefined;
            this.correctAnswer = undefined;
            this.currentDialogIndex = avalation.currentDialogIndex;
            this.defineStory();
          }, 5000);
        }
      });
    }
  }

  getStoryGenre(): string {
    return GENRE_OPTIONS.find(op => op.value === this.story?.genre)?.label ?? '--';
  }

  getStoryStatus(): string {
    return STORY_STATUS_OPTIONS.find(op => op.value === this.story?.storyStatus)?.label ?? '--';
  }

  getStoryCourseName(): string {
    return COURSE_NAME_OPTIONS.find(op => op.value === this.courseItem?.name)?.label ?? '--';
  }

  getNarrationAnswerSteps(): IStepGroup[] {
    if (!this.story?.chapters?.length) return [];

    const chapter = this.story.chapters[0];

    const stepsMap = new Map<number, IStepGroup>();

    for (let i = 1; i <= this.currentStep; i++) {
      stepsMap.set(i, {
        step: i,
        narration: null,
        answers: [],
      });
    }

    for (const narration of chapter.narrations) {
      if (narration.step <= this.currentStep) {
        const stepData = stepsMap.get(narration.step);
        if (stepData) stepData.narration = narration;
      }
    }

    for (const answer of chapter.answers) {
      if (answer.step <= this.currentStep) {
        const stepData = stepsMap.get(answer.step);
        if (stepData) stepData.answers.push(answer);
      }
    }

    return Array.from(stepsMap.values());
  }

  getIsSupportingCharacter(type: CharacterTypeEnum): boolean {
    return type === CharacterTypeEnum.SUPPORTING;
  }

  getIsAnswerCorrect(answerId: string): string | undefined {
    if (answerId === this.answerCheckedId) {
      return this.correctAnswer
        ? 'bg-green-500 animate-pulse'
        : 'bg-red-500 ring-2 ring-red-400 scale-105 transition-transform';
    }
    return undefined;
  }

  getShowAnswer(answer: IChapterAnswerItem): boolean {
    if (answer.answerIndex === this.currentDialogIndex) {
      return true;
    } else if (answer.answerIndex < this.currentDialogIndex && answer.correct) {
      return true;
    } else {
      return false;
    }
  }
}
