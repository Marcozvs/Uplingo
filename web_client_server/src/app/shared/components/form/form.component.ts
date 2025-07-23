import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormGroup, ReactiveFormsModule } from '@angular/forms';

import { FormlyFieldConfig, FormlyModule } from '@ngx-formly/core';


@Component({
  selector: 'app-form',
  imports: [CommonModule, ReactiveFormsModule, FormlyModule],
  templateUrl: './form.component.html',
  standalone: true,
})
export class FormComponent {
  @Input({ required: true }) form: FormGroup = new FormGroup({});

  @Input({ required: true }) model: any;

  @Input({ required: true }) formConfig: FormlyFieldConfig[] = [];

  @Output() blurEvent = new EventEmitter<any>();
}
