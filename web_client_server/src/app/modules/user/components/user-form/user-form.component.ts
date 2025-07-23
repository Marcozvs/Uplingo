import { CommonModule } from '@angular/common';
import { ChangeDetectorRef, Component, inject, Input, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';

import { FormlyFieldConfig } from '@ngx-formly/core';
import { TranslateService } from '@ngx-translate/core';
import { Observable, of, take, tap } from 'rxjs';

import { IUser, IUserUpdate } from '@modules/user/interfaces/user.interfaces';
import { defineUserFormField } from '@modules/user/components/user-form/user-form-field-group';
import { UserService } from '@modules/user/services/user.service';
import { UserFacade } from '@modules/user/store/user.facade';
import { CourseNameEnum } from '@modules/course/enums/course.enums';
import { createFormGroup } from '@shared/utils/form.utils';
import { FormComponent } from '@shared/components/form/form.component';
import { LayoutFacade } from '@layout/store/layout.facade';

interface IUserUpdateForm extends IUserUpdate {
  courseName: CourseNameEnum;
}

@Component({
  standalone: true,
  imports: [
    CommonModule,
    FormComponent
  ],
  selector: 'app-user-form',
  templateUrl: './user-form.component.html',
})
export class UserFormComponent implements OnInit {
  @Input() user: IUser | undefined;

  private readonly userFacade: UserFacade = inject(UserFacade);
  private readonly layoutFacade: LayoutFacade = inject(LayoutFacade);
  private readonly translateService: TranslateService = inject(TranslateService);
  private readonly changeDetectorRef: ChangeDetectorRef = inject(ChangeDetectorRef);
  private readonly userService: UserService = inject(UserService);

  model: IUserUpdateForm | undefined;
  form: FormGroup = new FormGroup({});
  formConfig: FormlyFieldConfig[] = [];

  ngOnInit(): void {
    this.defineModel();
  }

  private defineModel(): void {
    this.userFacade.user$.subscribe(user => {
      this.model = user
        ? {
          id: user.id,
          description: user.description,
          reasonsLearn: user.reasonsLearn,
          userLanguage: user.userLanguage,
          firstTime: false,
          courseName: CourseNameEnum.ENGLISH_COURSE,
        }
        : undefined;

      const savedCourse = localStorage.getItem('app-course');
      if (savedCourse && this.model)
        this.model.courseName = savedCourse as CourseNameEnum;

      let langCode = 'en';
      switch (this.model?.userLanguage) {
        case 'ENGLISH': langCode = 'en'; break;
        case 'SPANISH': langCode = 'sp'; break;
        case 'FRENCH': langCode = 'fr'; break;
        case 'PORTUGUESE_BR': langCode = 'pt'; break;
      }

      this.translateService.use(langCode);

      this.defineForm();
      this.defineFormConfig();
      this.watchLanguageChange();
    });
  }

  private defineForm(): void {
    if (this.model)
      this.form = createFormGroup(this.model);
  }

  private defineFormConfig(): void {
    this.formConfig = [
      {
        fieldGroupClassName: 'flex flex-col gap-4 max-w-full',
        fieldGroup: defineUserFormField({
          getModel: () => this.model,
          updateModel: (patch: Partial<IUserUpdateForm>) => {
            this.model = { ...this.model, ...patch } as IUserUpdateForm;
          },
          translate: this.translateService,
          layoutFacade: this.layoutFacade
        }),
      },
    ];
  }

  private watchLanguageChange(): void {
    this.translateService.onLangChange.subscribe(() => {
      this.defineFormConfig();
      this.changeDetectorRef.detectChanges();
    });
  }

  submit(): Observable<IUser | null> {
    if (!this.form.valid || !this.model || !this.model.id) {
      return of(null);
    }

    const { courseName, ...userUpdate } = this.model;

    return this.userService.update(userUpdate).pipe(
      take(1),
      tap(updatedUser => {
        this.userFacade.setUserData({ user: updatedUser });
        this.model = { ...updatedUser, courseName: this.model?.courseName! };
        this.defineForm();
        this.defineFormConfig();
        this.changeDetectorRef.detectChanges();
      })
    );
  }
}
