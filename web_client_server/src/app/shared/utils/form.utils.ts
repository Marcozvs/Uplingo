import { FormControl, FormGroup } from '@angular/forms';

export const createFormGroup = (
  model: Record<string, any>,
  excludeFields: string[] = []
): FormGroup => {
  const group: Record<string, FormControl> = {};

  Object.keys(model).forEach(key => {
    if (!excludeFields.includes(key)) {
      group[key] = new FormControl(model[key]);
    }
  });

  return new FormGroup(group);
};
