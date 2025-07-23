import { FormlyFieldConfig } from '@ngx-formly/core';
import { TranslateService } from '@ngx-translate/core';

interface IDependecies {
  getModel: () => { search: string | undefined } | undefined;
  updateModel: (patch: Partial<{ search: string | undefined }>) => void;
  translate: TranslateService;
  onSearchChange: () => void;
}

export function defineUserListFormField(dependencies: IDependecies): FormlyFieldConfig[] {
  const { translate, onSearchChange } = dependencies;

  const searchField: FormlyFieldConfig = {
    key: 'search',
    type: 'input-text',
    defaultValue: '',
    props: {
      label: translate.instant('FORM.FIELD.SEARCH.LABEL'),
      placeholder: translate.instant('FORM.FIELD.SEARCH.PLACEHOLDER'),
      input: () => onSearchChange(),
      blur: () => onSearchChange(),
      keydown: (field, event: KeyboardEvent) => {
        if (event.key === 'Enter') {
          onSearchChange();
        }
      }
    },
  };

  return [searchField];
}

