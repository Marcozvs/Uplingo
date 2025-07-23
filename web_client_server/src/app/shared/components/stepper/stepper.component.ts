import { CommonModule } from '@angular/common';
import { Component, Input, Output, EventEmitter } from '@angular/core';

import { StepperEnum } from '@shared/enum/stepper.enums';
import { IStep } from '@shared/interfaces/stepper.interfaces';

@Component({
  standalone: true,
  selector: 'app-stepper',
  imports: [CommonModule],
  templateUrl: './stepper.component.html',
})
export class StepperComponent {
  @Input() steps: IStep[] = [];
  @Input() activeStep = 0;
  @Input() mode: StepperEnum = StepperEnum.TEXT;
  @Input() allowClick = true;

  @Output() stepChangedEvent = new EventEmitter<number>();

  StepperEnum = StepperEnum;

  isCompleted(index: number): boolean {
    return index < this.activeStep;
  }

  isActive(index: number): boolean {
    return index <= this.activeStep;
  }

  handleStepChanged(index: number): void {
    if (!this.allowClick) return;

    if (index >= 0 && index < this.steps.length && index !== this.activeStep) {
      this.activeStep = index;
      this.stepChangedEvent.emit(this.activeStep);
    }
  }

}
