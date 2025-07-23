import { CommonModule } from '@angular/common';
import { Component, inject, OnInit } from '@angular/core';

import { TranslateModule } from '@ngx-translate/core';

import { ModalComponent } from '@shared/components/modal/modal.component';
import { ProfileFormComponent } from '@pages/profile/components/profile-form/profile-form.component';
import { ProfileViewComponent } from '@pages/profile/components/profile-view/profile-view.component';
import { UserService } from '@modules/user/services/user.service';
import { IUser } from '@modules/user/interfaces/user.interfaces';
import { ActivatedRoute } from '@angular/router';
import { UserFacade } from '@modules/user/store/user.facade';
import { take } from 'rxjs';

@Component({
  standalone: true,
  selector: 'app-profile-page',
  imports: [CommonModule, ProfileViewComponent, ProfileFormComponent, ModalComponent, TranslateModule],
  templateUrl: './profile-page.component.html'
})
export class ProfilePageComponent implements OnInit {
  private readonly userService: UserService = inject(UserService);
  private readonly activatedRoute: ActivatedRoute = inject(ActivatedRoute);
  private readonly userFacade: UserFacade = inject(UserFacade);

  showForm: boolean = false;
  userForm: boolean = true;
  user: IUser | undefined;

  ngOnInit(): void {
    this.defineUser();
  }

  private defineUser(): void {
    this.userService.read(this.activatedRoute.snapshot.params['id']).pipe(take(1)).subscribe({
      next: (user: IUser) => {
        this.user = user;
      }
    })
  }

  handleFormVisibility(userForm: boolean): void {
    this.userForm = userForm;
    this.showForm = this.showForm ? false : true;
  }

  handleProfileUpdated(): void {
    this.showForm = false;
    this.defineUser();
  }
}
