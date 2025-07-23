import { CommonModule } from '@angular/common';
import { Component, inject, Input, OnInit, OnDestroy } from '@angular/core';

import { LayoutFacade } from '@layout/store/layout.facade';

import { Observable, Subscription } from 'rxjs';

@Component({
  selector: 'app-icon-button',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './icon-button.component.html'
})
export class IconButtonComponent implements OnInit, OnDestroy {
  @Input({ required: true }) icon!: string;
  @Input() disabled: boolean = false;
  @Input() ariaLabel: string = 'icon button';

  private readonly layoutFacade = inject(LayoutFacade);
  loading$: Observable<boolean> = this.layoutFacade.loading$;

  private loadingSubscription?: Subscription;
  isLoading = false;

  readonly baseClasses = 'flex items-center justify-center w-10 h-10 rounded-full transition-colors duration-300 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-gray-400';

  ngOnInit(): void {
    if (!this.icon) {
      throw new Error('Input "icon" is required for IconButtonComponent.');
    }
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
    return this.isDisabled ? 'opacity-50 cursor-not-allowed' : 'cursor-pointer';
  }


  get bgClasses(): string {
    return this.isDisabled
      ? 'bg-gray-300'
      : 'bg-gray-300 hover:bg-gray-400 dark:bg-gray-600 dark:hover:bg-gray-500';
  }
}
