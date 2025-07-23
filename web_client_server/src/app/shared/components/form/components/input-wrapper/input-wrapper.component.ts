import { CommonModule } from '@angular/common';
import { ChangeDetectionStrategy, Component } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';

import { FieldWrapper, FormlyModule } from '@ngx-formly/core';

@Component({
  standalone: true,
  selector: 'app-input-wrapper',
  imports: [CommonModule, FormlyModule, ReactiveFormsModule],
  templateUrl: './input-wrapper.component.html'
})
export class InputWrapperComponent extends FieldWrapper {}
