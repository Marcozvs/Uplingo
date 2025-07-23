import { ConfigOption, FormlyExtension } from '@ngx-formly/core';

import { InputTextComponent } from '@shared/components/form/components/input-text/input-text.component';
import { InputWrapperComponent } from '@shared/components/form/components/input-wrapper/input-wrapper.component';
import { SelectComponent } from '@shared/components/form/components/select/select.component';
import { TextareaComponent } from '@shared/components/form/components/textarea/textarea.component';

const defaultLabelExtension: FormlyExtension = {
  prePopulate(field): void {
    if (field.props?.label) {
      return;
    }
    field.props = {
      ...field.props,
      label: 'Default Label',
    };
  },
};

export const formlyConfig: ConfigOption = {
  types: [
    {
      name: 'input-text',
      component: InputTextComponent,
      wrappers: ['input-wrapper'],
    },
    {
      name: 'select',
      component: SelectComponent,
      wrappers: ['input-wrapper'],
    },
    {
      name: 'textarea',
      component: TextareaComponent,
      wrappers: ['input-wrapper'],
    },
  ],
  extensions: [
    {
      name: 'default-label',
      extension: defaultLabelExtension,
    },
  ],
  wrappers: [{ name: 'input-wrapper', component: InputWrapperComponent }],
  validationMessages: [{ name: 'required', message: 'Field required' }],
};
