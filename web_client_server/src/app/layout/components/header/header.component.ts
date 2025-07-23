import { CommonModule } from '@angular/common';
import { Component, inject, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { Observable, take } from 'rxjs';

import { IUser } from '@modules/user/interfaces/user.interfaces';
import { UserFacade } from '@modules/user/store/user.facade';
import { IconButtonComponent } from '@shared/components/icon-button/icon-button.component';

@Component({
  standalone: true,
  selector: 'app-header',
  imports: [CommonModule],
  templateUrl: './header.component.html',
})
export class HeaderComponent implements OnInit {
  @Input() hasResources: boolean = true;

  user$: Observable<IUser | undefined> | undefined;

  private readonly userFacade: UserFacade = inject(UserFacade);
  readonly router: Router = inject(Router);

  ngOnInit(): void {
    this.defineUserResources();
  }

  private defineUserResources(): void {
    this.user$ = this.userFacade.user$;
  }

  handleNavigateToProfile(): void {
    this.user$?.pipe(take(1)).subscribe({
      next: (userLogged: IUser | undefined) => {
        if (userLogged)
          this.router.navigate([`/profile/${userLogged.id}`]);
      }
    })
  }
}
