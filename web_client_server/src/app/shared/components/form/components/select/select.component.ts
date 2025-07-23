import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';

import { isObservable, Subscription } from 'rxjs';

import { TranslateModule } from '@ngx-translate/core';

import { FieldType, FieldTypeConfig, FormlyModule } from '@ngx-formly/core';

@Component({
  standalone: true,
  selector: 'app-select',
  imports: [CommonModule, ReactiveFormsModule, FormlyModule, TranslateModule],
  templateUrl: './select.component.html',
})
export class SelectComponent extends FieldType<FieldTypeConfig> implements OnInit {
  optionsArray: any[] = [];
  private subscription?: Subscription;

  ngOnInit(): void {
    this.applyValidators();

    if (isObservable(this.props.options)) {
      this.subscription = this.props.options.subscribe((opts: any[]) => {
        this.optionsArray = opts;
      });
    } else if (Array.isArray(this.props.options)) {
      this.optionsArray = this.props.options;
    }
  }

  ngOnDestroy(): void {
    this.subscription?.unsubscribe();
  }

  private applyValidators(): void {
    if (this.field.validators?.validation) {
      this.formControl.setValidators(this.field.validators.validation);
      this.formControl.updateValueAndValidity();
    }
  }
}
