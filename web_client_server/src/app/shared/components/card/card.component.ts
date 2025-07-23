import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';

import { IconButtonComponent } from '@shared/components/icon-button/icon-button.component';

@Component({
  selector: 'app-card',
  standalone: true,
  imports: [
    CommonModule,
    IconButtonComponent,
  ],
  templateUrl: './card.component.html',
})
export class CardComponent {
  @Input() icon?: string;
  @Input() iconColor?: string;
  @Input() imageUrl?: string;
  @Input() title?: string;
  @Input() content?: string;
  @Input() hoover: boolean = false;
  @Input() clickable: boolean = false;
  @Input() background?: string;
  @Input() hoverBackground?: string;
  @Input() padding?: string;
  @Input() hasMenu?: boolean = false;
  @Input() titleColor?: string;
  @Input() contentColor?: string;
  @Input() translucent: boolean = false;
  @Output() clickEvent = new EventEmitter<void>();
  @Output() openMenuEvent = new EventEmitter<void>();

  get cardClasses(): string[] {
    const baseBg = this.background || 'bg-gray-50 dark:bg-gray-800';
    const hoverBg = this.hoover
      ? this.hoverBackground || 'hover:bg-gray-100 dark:hover:bg-gray-700'
      : '';
    const opacityClass = this.translucent ? 'bg-opacity-70 backdrop-blur-sm' : '';
    const paddingClass = this.padding || 'p-4';

    return [
      'w-full',
      'flex',
      'gap-4',
      'items-center',
      'rounded-md',
      baseBg,
      opacityClass,
      hoverBg,
      paddingClass,
      'transition',
      'duration-500',
      this.clickable ? 'cursor-pointer' : '',
    ];
  }

  get titleClasses(): string {
    return this.titleColor || 'text-gray-800 dark:text-gray-100';
  }

  get contentClasses(): string {
    return this.contentColor || 'text-gray-700 dark:text-gray-300';
  }


  get iconClasses(): string {
    return this.iconColor || 'text-orange-500';
  }

  handleCardClick(): void {
    if (this.clickable) {
      this.clickEvent.emit();
    }
  }

  handleMenuClick(event: Event): void {
    event.stopPropagation();
    this.openMenuEvent.emit();
  }
}
