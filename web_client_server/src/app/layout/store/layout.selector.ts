import { layoutFeature } from "@layout/store/layout.reducer";

export const {
  selectLayoutState,
  selectDarkMode,
  selectLanguage,
  selectLoading,
  selectToastQueue,
  selectSidebar
} = layoutFeature;

export const layoutSelector = {
  selectLayoutState,
  selectDarkMode,
  selectLanguage,
  selectLoading,
  selectToastQueue,
  selectSidebar
};

