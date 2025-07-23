import { CommonModule } from '@angular/common';
import { Component, inject, Input, OnInit, OnDestroy, ChangeDetectionStrategy } from '@angular/core';

import { LayoutFacade } from '@layout/store/layout.facade';

import { Observable, Subscription } from 'rxjs';

type ButtonSeverity = 'default' | 'outline' | 'text';

@Component({
  selector: 'app-button',
  standalone: true,
  imports: [CommonModule],
  changeDetection: ChangeDetectionStrategy.OnPush,
  templateUrl: './button.component.html'
})
export class ButtonComponent implements OnInit, OnDestroy {
  @Input() text: string = 'Confirm';
  @Input() icon?: string;
  @Input() severity: ButtonSeverity = 'default';
  @Input() disabled: boolean = false;

  private readonly layoutFacade = inject(LayoutFacade);
  loading$: Observable<boolean> = this.layoutFacade.loading$;

  private loadingSubscription?: Subscription;

  isLoading = false;

  readonly baseClasses = 'flex gap-4 items-center justify-center font-medium rounded-lg text-sm px-8 py-2.5 me-2 mb-2 focus:outline-none transition duration-300';

  readonly severityClasses: Record<ButtonSeverity, string> = {
    default:
      'text-white bg-orange-400 hover:bg-orange-500 focus:ring-orange-300 dark:bg-orange-500 dark:hover:bg-orange-400 dark:focus:ring-orange-700',
    outline:
      'text-orange-400 border border-orange-400 bg-white hover:bg-orange-50 focus:ring-orange-300 dark:bg-gray-900 dark:text-orange-400 dark:border-orange-400',
    text:
      'text-orange-400 bg-transparent hover:bg-orange-50 focus:ring-orange-300 dark:text-orange-400'
  };


  ngOnInit(): void {
    this.loadingSubscription = this.loading$.subscribe(value => {
      this.isLoading = value;
    });
  }

  ngOnDestroy(): void {
    this.loadingSubscription?.unsubscribe();
  }

  get isDisabled(): boolean {
    return this.disabled || this.isLoading;
  }

  get disabledClasses(): string {
    return this.isDisabled
      ? 'opacity-50 bg-gray-200 dark:bg-gray-700 text-gray-400 dark:text-gray-500 border-gray-300 dark:border-gray-600'
      : '';
  }

  get cursorClass(): string {
    return this.isDisabled ? 'cursor-not-allowed' : 'cursor-pointer';
  }
}
