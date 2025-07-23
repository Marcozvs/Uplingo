import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { ModalComponent } from '@shared/components/modal/modal.component';
import { ButtonComponent } from '@shared/components/button/button.component';

@Component({
  selector: 'app-confirmation-modal',
  standalone: true,
  imports: [CommonModule, ModalComponent, ButtonComponent],
  templateUrl: './confirmation.component.html',
})
export class ConfirmationModalComponent {
  @Output() confirmed = new EventEmitter<void>();
  @Output() cancelled = new EventEmitter<void>();
  @Input() content: string = 'Esta ação é irreversível. Tem certeza que deseja continuar?'
  @Input() open: boolean = false;

  openModal(): void {
    this.open = true;
  }

  closeModal(): void {
    this.open = false;
    this.cancelled.emit();
  }

  confirm(): void {
    this.open = false;
    this.confirmed.emit();
  }
}
