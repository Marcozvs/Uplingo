import { AuthConfig } from 'angular-oauth2-oidc';
import { environment } from '@environments/environment';

export const authCodeFlowConfig: AuthConfig = {
  issuer: environment.auth.issuer,
  redirectUri: window.location.origin + '/login/callback',
  clientId: environment.auth.clientId,
  responseType: environment.auth.responseType,
  scope: environment.auth.scope,
  showDebugInformation: environment.auth.showDebugInformation,
  requireHttps: environment.auth.requireHttps,
};
