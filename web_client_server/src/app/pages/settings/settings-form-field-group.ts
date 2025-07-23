import { FormlyFieldConfig } from '@ngx-formly/core';
import { TranslateService } from '@ngx-translate/core';
import { LanguageEnum } from '@shared/enum/language.enums';
import { LANGUAGE_OPTIONS } from '@shared/constants/language.constants';
import { LayoutFacade } from '@layout/store/layout.facade';

interface ISettings {
  language: LanguageEnum;
  darkMode: boolean;
}

interface IDependecies {
  getModel: () => ISettings | undefined;
  updateModel: (patch: Partial<ISettings>) => void;
  translate: TranslateService;
  layoutFacade: LayoutFacade;
}

export function defineSettingsFormField(dependencies: IDependecies): FormlyFieldConfig[] {
  const { translate, updateModel, layoutFacade } = dependencies;

  const languageField: FormlyFieldConfig = {
    key: 'language',
    type: 'select',
    defaultValue: '',
    props: {
      label: translate.instant('FORM.FIELD.LANGUAGE.LABEL'),
      placeholder: translate.instant('FORM.FIELD.LANGUAGE.PLACEHOLDER'),
      required: true,
      options: LANGUAGE_OPTIONS,
      handleChange: (event: Event) => {
        const value = (event.target as HTMLSelectElement).value;
        const langEnum = LANGUAGE_OPTIONS.find(op => op.value === value)?.value;
        if (langEnum === undefined) return;

        const langCode = (() => {
          switch (langEnum) {
            case 'ENGLISH': return 'en';
            case 'SPANISH': return 'sp';
            case 'FRENCH': return 'fr';
            case 'PORTUGUESE_BR': return 'pt';
            default: return 'en';
          }
        })();

        translate.use(langCode);

        layoutFacade.setLanguage(langEnum);

        updateModel({ language: langEnum });
      }
    },
  };

  const darkModeField: FormlyFieldConfig = {
    key: 'darkMode',
    type: 'select',
    defaultValue: false,
    props: {
      label: translate.instant('FORM.FIELD.DARK_MODE.LABEL'),
      placeholder: translate.instant('FORM.FIELD.DARK_MODE.PLACEHOLDER'),
      required: true,
      options: [
        { value: true, label: 'FORM.FIELD.DARK_MODE.ENABLED' },
        { value: false, label: 'FORM.FIELD.DARK_MODE.DISABLED' }
      ],
      handleChange: (event: Event) => {
        const value = (event.target as HTMLSelectElement).value;
        const darkMode = value === 'true';
        sessionStorage.setItem('darkMode', darkMode.toString());
        layoutFacade.toggleDarkMode();
        updateModel({ darkMode });
      },
    },
  };

  return [languageField, darkModeField];
}
