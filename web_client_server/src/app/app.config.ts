import {
  APP_INITIALIZER,
  ApplicationConfig,
  importProvidersFrom,
  provideZoneChangeDetection,
} from '@angular/core';
import { provideRouter } from '@angular/router';
import {
  HttpClient,
  provideHttpClient,
  withInterceptors,
} from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';

import { provideStore, Store } from '@ngrx/store';
import { provideEffects } from '@ngrx/effects';
import { provideOAuthClient } from 'angular-oauth2-oidc';
import { Observable, of } from 'rxjs';
import { FormlyModule } from '@ngx-formly/core';
import {
  provideTranslateService,
  TranslateLoader,
  TranslateService,
} from '@ngx-translate/core';

import { authCodeFlowConfig } from './auth.config';
import { HttpLoaderFactory } from './translate.config';
import { formlyConfig } from './formly.config';
import { routes } from './app.routes';
import { reducers } from './app.reducer';
import { authTokenInterceptor } from '@core/interceptors/authentication/authentication.interceptor';
import { userActions } from '@modules/user/store/user.actions';
import { UserEffects } from '@modules/user/store/user.effects';
import { LayoutEffects } from '@layout/store/layout.effects';
import { StoryEffects } from '@modules/story/store/story.effects';
import { CourseEffects } from '@modules/course/store/course.effects';

function initializeAuthConfiguration(store: Store): () => Observable<boolean> {
  return (): Observable<boolean> => {
    store.dispatch(userActions.setOAuthConfig({ config: authCodeFlowConfig }));
    return of(true);
  };
}

function initializeTranslation(translate: TranslateService): () => Promise<void> {
  return () => {
    const sessionLang = sessionStorage.getItem('app-language');
    const langCode = sessionLang || 'en';
    translate.setDefaultLang(langCode);
    sessionStorage.setItem('app-language', langCode);
    translate.use(langCode);
    return Promise.resolve();
  };
}

export const appConfig: ApplicationConfig = {
  providers: [
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideHttpClient(withInterceptors([authTokenInterceptor])),
    provideRouter(routes),
    provideStore(reducers),
    provideEffects([UserEffects, LayoutEffects, StoryEffects, CourseEffects]),
    provideHttpClient(),
    provideOAuthClient(),
    {
      provide: APP_INITIALIZER,
      useFactory: initializeAuthConfiguration,
      deps: [Store],
      multi: true,
    },
    {
      provide: APP_INITIALIZER,
      useFactory: initializeTranslation,
      deps: [TranslateService],
      multi: true,
    },
    provideTranslateService({
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpClient],
      },
    }),
    importProvidersFrom(
      ReactiveFormsModule,
      FormlyModule.forRoot(formlyConfig)
    )
  ],
};
