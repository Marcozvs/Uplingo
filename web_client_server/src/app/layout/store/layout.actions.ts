import { createActionGroup, emptyProps, props } from '@ngrx/store';

import { IToast } from '@shared/interfaces/toast.interfaces';
import { LayoutState } from './layout.interface';


export const layoutActions = createActionGroup({
  source: 'Layout',
  events: {
    setData: props<{ data: Partial<LayoutState> }>(),
    toggleTheme: emptyProps(),
    loading: emptyProps(),
    sidebar: emptyProps(),
    addToast: props<{ toast: IToast }>(),
    setToastDisplayed: props<{ id: string }>(),
    removeToast: props<{ toast: IToast }>(),
  },
});
