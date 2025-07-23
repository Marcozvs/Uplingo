import { CommonModule } from '@angular/common';
import { Component, inject, OnInit } from '@angular/core';

import { OAuthService } from 'angular-oauth2-oidc';

@Component({
  standalone: true,
  selector: 'app-logout',
  imports: [CommonModule],
  templateUrl: './logout.component.html',
})
export class LogoutComponent implements OnInit {
  private oauthService: OAuthService = inject(OAuthService);

  ngOnInit(): void {
    this.oauthService.logOut();
    window.location.href = 'http://localhost:4203/logout';
  }
}
