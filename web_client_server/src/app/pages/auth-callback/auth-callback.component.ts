import { Component, OnInit, OnDestroy, inject } from '@angular/core';
import { Router } from '@angular/router';

import { Subject, takeUntil, filter, take, switchMap } from 'rxjs';

import { UserFacade } from '@modules/user/store/user.facade';

@Component({
  standalone: true,
  selector: 'app-auth-callback',
  templateUrl: './auth-callback.component.html',
})
export class AuthCallbackComponent implements OnInit, OnDestroy {
  private readonly userFacade = inject(UserFacade);
  private readonly router = inject(Router);
  private readonly destroy$ = new Subject<void>();

  ngOnInit(): void {
    this.userFacade.logInRequestHandled$.pipe(
      filter((handled): handled is true => handled === true),
      take(1),
      takeUntil(this.destroy$),
      switchMap(() => this.userFacade.loggedIn$.pipe(
        take(1),
        filter((loggedIn): loggedIn is true => loggedIn === true)
      )),
      switchMap(() => this.userFacade.user$.pipe(take(1)))
    ).subscribe({
      next: (user) => {
        if (user?.firstTime) {
          this.router.navigate(['//welcome']);
        } else {
          this.router.navigate(['/story']);
        }
      },
      error: (error) => {
        this.router.navigate(['/story']);
      }
    });
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
}
