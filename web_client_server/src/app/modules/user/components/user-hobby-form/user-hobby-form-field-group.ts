import { FormlyFieldConfig } from '@ngx-formly/core';

import { TranslateService } from '@ngx-translate/core';

import { IUserHobbyCreate } from '@modules/user/interfaces/hobby.interfaces';

interface IDependencies {
  getModel: () => IUserHobbyCreate | undefined;
  updateModel: (patch: IUserHobbyCreate | undefined) => void;
  translate: TranslateService;
}

export function defineUserHobbyFormField(dependencies: IDependencies): FormlyFieldConfig[] {
  const { translate } = dependencies;

  return [
    {
      key: 'description',
      type: 'textarea',
      props: {
        label: translate.instant('FORM.FIELD.HOBBY.LABEL'),
        placeholder: translate.instant('FORM.FIELD.HOBBY.PLACEHOLDER'),
        rows: 4,
        required: true,
      },
    },
  ];
}
