import { CommonModule } from '@angular/common';
import { Component, inject, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';

import { take } from 'rxjs';
import { FormlyFieldConfig } from '@ngx-formly/core';
import { TranslateService } from '@ngx-translate/core';

import { IUser } from '@modules/user/interfaces/user.interfaces';
import { UserFacade } from '@modules/user/store/user.facade';
import { LanguageEnum } from '@shared/enum/language.enums';
import { createFormGroup } from '@shared/utils/form.utils';
import { defineSettingsFormField } from '@pages/settings/settings-form-field-group';
import { FormComponent } from '@shared/components/form/form.component';
import { LayoutFacade } from '@layout/store/layout.facade';

interface ISettings {
  language: LanguageEnum,
  darkMode: boolean
}

@Component({
  standalone: true,
  selector: 'app-settings',
  imports: [CommonModule, FormComponent],
  templateUrl: './settings.component.html',
})
export class SettingsComponent implements OnInit {
  private readonly userFacade: UserFacade = inject(UserFacade);
  private readonly layoutFacade: LayoutFacade = inject(LayoutFacade);
  private readonly translateService: TranslateService = inject(TranslateService);

  model: ISettings | undefined;
  form: FormGroup = new FormGroup({});
  formConfig: FormlyFieldConfig[] = [];

  ngOnInit(): void {
    this.defineModel();
    this.translateService.onLangChange.subscribe(() => {
      this.defineFormConfig();
      this.defineForm();
    });
  }

  private defineModel(): void {
    this.userFacade.user$
      .pipe(take(1))
      .subscribe({
        next: (user: IUser | undefined) => {
          if (!user) return;
          const darkMode = sessionStorage.getItem('darkMode') === 'true';
          this.model = {
            language: user.userLanguage,
            darkMode,
          };
        },
        complete: () => {
          this.defineForm();
          this.defineFormConfig();
        }
      })
  }


  private defineForm(): void {
    if (this.model)
      this.form = createFormGroup(this.model);
  }

  private defineFormConfig(): void {
    this.formConfig = [
      {
        fieldGroupClassName: 'flex flex-col gap-4 max-w-full',
        fieldGroup: defineSettingsFormField({
          getModel: () => this.model,
          updateModel: (patch: Partial<ISettings>) => {
            this.model = { ...this.model, ...patch } as ISettings;
          },
          translate: this.translateService,
          layoutFacade: this.layoutFacade,
        }),
      },
    ];
  }
}
