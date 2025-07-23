import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CardComponent } from '@shared/components/card/card.component';
import { IconButtonComponent } from '@shared/components/icon-button/icon-button.component';

@Component({
  selector: 'app-modal',
  standalone: true,
  imports: [CommonModule, CardComponent, IconButtonComponent],
  templateUrl: './modal.component.html',
})
export class ModalComponent {
  @Input() open: boolean = false;
  @Output() closeModalEvent = new EventEmitter<void>();

  @Input() icon?: string;
  @Input() iconColor?: string;
  @Input() imageUrl?: string;
  @Input() title?: string;
  @Input() content?: string;
  @Input() hoover: boolean = false;
  @Input() clickable: boolean = false;
  @Input() background?: string;
  @Input() hoverBackground?: string;
  @Input() hasMenu?: boolean = false;
  @Input() titleColor?: string;
  @Input() contentColor?: string;
  @Input() translucent: boolean = false;
}
