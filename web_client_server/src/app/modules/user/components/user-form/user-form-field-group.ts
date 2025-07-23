import { FormlyFieldConfig } from '@ngx-formly/core';
import { TranslateService } from '@ngx-translate/core';

import { CourseNameEnum } from '@modules/course/enums/course.enums';
import { IUserUpdate } from '@modules/user/interfaces/user.interfaces';
import { COURSE_NAME_OPTIONS } from '@modules/course/constants/course.constants';
import { getLanguageCode, LANGUAGE_OPTIONS } from '@shared/constants/language.constants';
import { LanguageEnum } from '@shared/enum/language.enums';
import { LayoutFacade } from '@layout/store/layout.facade';

interface IUserUpdateForm extends IUserUpdate {
  courseName: CourseNameEnum;
}

interface IDependecies {
  getModel: () => IUserUpdateForm | undefined;
  updateModel: (patch: Partial<IUserUpdateForm>) => void;
  translate: TranslateService;
  layoutFacade: LayoutFacade;
}

export function defineUserFormField(dependencies: IDependecies): FormlyFieldConfig[] {
  const { translate, updateModel, getModel, layoutFacade } = dependencies;

  const languageField: FormlyFieldConfig = {
    key: 'userLanguage',
    type: 'select',
    defaultValue: '',
    props: {
      label: translate.instant('FORM.FIELD.NATIVE_LANGUAGE.LABEL'),
      placeholder: translate.instant('FORM.FIELD.NATIVE_LANGUAGE.PLACEHOLDER'),
      required: true,
      options: LANGUAGE_OPTIONS,
      handleChange: (event: Event) => {
        const value = (event.target as HTMLSelectElement).value as LanguageEnum;
        const langEnum = LANGUAGE_OPTIONS.find(op => op.value === value)?.value;
        if (!langEnum) return;

        const langCode = getLanguageCode(langEnum);
        translate.use(langCode);
        layoutFacade.setLanguage(langEnum);

        const currentCourseName = getModel()?.courseName ?? CourseNameEnum.ENGLISH_COURSE;
        updateModel({
          courseName: currentCourseName,
          userLanguage: langEnum,
        });
      },
    },
  };

  const courseField: FormlyFieldConfig = {
    key: 'courseName',
    type: 'select',
    defaultValue: '',
    props: {
      label: translate.instant('FORM.FIELD.COURSE_NAME.LABEL'),
      placeholder: translate.instant('FORM.FIELD.COURSE_NAME.PLACEHOLDER'),
      required: true,
      options: COURSE_NAME_OPTIONS,
      handleChange: (event: Event) => {
        const value = (event.target as HTMLSelectElement).value;
        localStorage.setItem('app-course', value);
        updateModel({ courseName: value as CourseNameEnum });
      },
    },
  };

  const descriptionField: FormlyFieldConfig = {
    key: 'description',
    type: 'textarea',
    defaultValue: '',
    props: {
      label: translate.instant('FORM.FIELD.DESCRIPTION.LABEL'),
      placeholder: translate.instant('FORM.FIELD.DESCRIPTION.PLACEHOLDER'),
      rows: 4,
      required: true,
    },
  };

  const reasonsLearnField: FormlyFieldConfig = {
    key: 'reasonsLearn',
    type: 'textarea',
    defaultValue: '',
    props: {
      label: translate.instant('FORM.FIELD.REASONS_LEARN.LABEL'),
      placeholder: translate.instant('FORM.FIELD.REASONS_LEARN.PLACEHOLDER'),
      rows: 4,
      required: true,
    },
  };

  return [languageField, courseField, descriptionField, reasonsLearnField];
}
