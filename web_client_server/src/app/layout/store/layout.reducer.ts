import { createFeature, createReducer, on } from '@ngrx/store';

import { LayoutState } from '@layout/store/layout.interface';
import { layoutActions } from '@layout/store//layout.actions';
import { LanguageEnum } from '@shared/enum/language.enums';

export const layoutInitialState: LayoutState = {
  darkMode: false,
  language: LanguageEnum.ENGLISH,
  loading: false,
  toastQueue: [],
  sidebar: true,
};

export const layoutFeature = createFeature({
  name: 'layout',
  reducer: createReducer(
    layoutInitialState,
    on(layoutActions.setData, (state, { data }) => ({ ...state, ...data })),
    on(layoutActions.toggleTheme, state => ({ ...state, darkMode: !state.darkMode })),
    on(layoutActions.loading, state => ({ ...state, loading: !state.loading })),
    on(layoutActions.sidebar, state => ({ ...state, sidebar: !state.sidebar })),
    on(layoutActions.addToast, (state, { toast }) => ({
      ...state,
      toastQueue: [...state.toastQueue, toast],
    })),
    on(layoutActions.setToastDisplayed, (state, { id }) => ({
      ...state,
      toastQueue: state.toastQueue.map(toast => {
        if (toast.id === id) {
          return {
            ...toast,
            displayed: true,
          };
        }
        return toast;
      }),
    })),
    on(layoutActions.removeToast, (state, { toast }) => ({
      ...state,
      toastQueue: state.toastQueue.filter(t => t.id !== toast.id),
    }))
  ),
});
