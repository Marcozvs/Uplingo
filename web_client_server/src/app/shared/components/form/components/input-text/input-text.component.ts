import { CommonModule } from '@angular/common';
import { ChangeDetectionStrategy, Component } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { FieldType, FieldTypeConfig, FormlyModule } from '@ngx-formly/core';

@Component({
  selector: 'app-input-text',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, FormlyModule],
  templateUrl: './input-text.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class InputTextComponent extends FieldType<FieldTypeConfig> {
  ngOnInit(): void {
    this.applyValidators();
  }

  private applyValidators(): void {
    if (this.field.validators?.validation) {
      this.formControl.setValidators(this.field.validators.validation);
      this.formControl.updateValueAndValidity();
    }
  }
}
