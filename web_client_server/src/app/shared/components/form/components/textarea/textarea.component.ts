import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';

import { FieldType, FieldTypeConfig, FormlyModule } from '@ngx-formly/core';

@Component({
  standalone: true,
  selector: 'app-textarea',
  imports: [CommonModule, ReactiveFormsModule, FormlyModule],
  templateUrl: './textarea.component.html',
})
export class TextareaComponent extends FieldType<FieldTypeConfig> {
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
