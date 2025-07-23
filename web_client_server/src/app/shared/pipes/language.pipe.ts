import { Pipe, PipeTransform } from '@angular/core';
import { LanguageEnum } from '@shared/enum/language.enums';
import { LANGUAGE_OPTIONS } from '../constants/language.constants';

@Pipe({
  name: 'language',
  standalone: true,
})
export class LanguagePipe implements PipeTransform {
  transform(value: LanguageEnum | undefined | null): string {
    if (value === null || value === undefined) {
      return '—';
    }

    const option = LANGUAGE_OPTIONS.find(opt => opt.value === value);
    return option ? option.label : '—';
  }
}
