import { Routes } from '@angular/router';

import { authenticationGuard } from '@core/guards/authentication/authentication.guard';

export const routes: Routes = [
  {
    path: '',
    redirectTo: 'story',
    pathMatch: 'full',
  },
  {
    path: 'story',
    loadComponent: () => import('@pages/story/story-page.component')
      .then(m => m.StoryPageComponent),
    data: {
      breadcrumb: 'ROUTES.BREADCRUMB.STORY',
    },
    canActivate: [authenticationGuard]
  },
  {
    path: 'story/:id',
    loadComponent: () => import('@pages/story/story-page.component')
      .then(m => m.StoryPageComponent),
    data: {
      breadcrumb: 'ROUTES.BREADCRUMB.STORY',
    },
    canActivate: [authenticationGuard]
  },
  {
    path: 'profile/:id',
    loadComponent: () => import('@pages/profile/profile-page.component')
      .then(m => m.ProfilePageComponent),
    data: {
      breadcrumb: 'ROUTES.BREADCRUMB.PROFILE',
    },
    canActivate: [authenticationGuard],
  },
  {
    path: 'welcome',
    loadComponent: () => import('@pages/welcome/welcome-page.component')
      .then(m => m.WelcomePageComponent),
    data: {
      breadcrumb: 'ROUTES.BREADCRUMB.WELCOME',
    },
    canActivate: [authenticationGuard],
  },
  {
    path: 'user-management',
    loadComponent: () => import('@pages/user-management/user-management-page.component')
      .then(m => m.UserManagementPageComponent),
    data: {
      breadcrumb: 'ROUTES.BREADCRUMB.USER_MANAGEMENT',
    },
    canActivate: [authenticationGuard],
  },
  {
    path: 'login/callback',
    loadComponent: () => import('@pages/auth-callback/auth-callback.component')
      .then(m => m.AuthCallbackComponent),
    data: {
      breadcrumb: 'ROUTES.BREADCRUMB.AUTH',
    },
  },
  {
    path: 'logout',
    loadComponent: () => import('@pages/logout/logout.component')
      .then(m => m.LogoutComponent),
    data: {
      breadcrumb: 'ROUTES.BREADCRUMB.LOGOUT',
    },
  },
  {
    path: 'settings',
    loadComponent: () => import('@pages/settings/settings.component')
      .then(m => m.SettingsComponent),
    data: {
      breadcrumb: 'ROUTES.BREADCRUMB.SETTINGS',
    },
  },
];
