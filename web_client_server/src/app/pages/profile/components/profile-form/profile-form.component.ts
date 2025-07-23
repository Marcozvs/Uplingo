import { CommonModule } from '@angular/common';
import { Component, EventEmitter, inject, Input, Output, ViewChild } from '@angular/core';

import { TranslateModule } from '@ngx-translate/core';
import { take } from 'rxjs';

import { IUserHobbyItem } from '@modules/user/interfaces/hobby.interfaces';
import { UserFormComponent } from '@modules/user/components/user-form/user-form.component';
import { UserHobbyFormComponent } from '@modules/user/components/user-hobby-form/user-hobby-form.component';
import { IUser } from '@modules/user/interfaces/user.interfaces';
import { UserFacade } from '@modules/user/store/user.facade';
import { ButtonComponent } from '@shared/components/button/button.component';

@Component({
  standalone: true,
  selector: 'app-profile-form',
  imports: [CommonModule, UserFormComponent, UserHobbyFormComponent, ButtonComponent, TranslateModule],
  templateUrl: './profile-form.component.html',
})
export class ProfileFormComponent {
  @Input({ required: true }) user: IUser | undefined;
  @Input({ required: true }) userForm = true;
  @Output() profileUpdatedEvent: EventEmitter<void> = new EventEmitter<void>();
  @ViewChild(UserFormComponent) userFormComponent?: UserFormComponent;
  @ViewChild(UserHobbyFormComponent) userHobbyFormComponent?: UserHobbyFormComponent;

  private readonly userFacade: UserFacade = inject(UserFacade);

  handleSubmit(): void {
    if (this.userForm) {
      this.userFormComponent?.submit().subscribe({
        next: (response) => {
          if (response) {
            if (response)
              this.userFacade.user$.pipe(take(1)).subscribe({
                next: (userLogged: IUser | undefined) => {
                  if (userLogged && userLogged.id === this.user?.id)
                    this.userFacade.setUserData({ user: response });
                },
                complete: () => {
                  this.profileUpdatedEvent.emit();
                }
              });
          }
        }
      });
    } else {
      this.userHobbyFormComponent?.submit().subscribe({
        next: (updatedHobbies: IUserHobbyItem[]) => {
          this.userFacade.user$.pipe(take(1)).subscribe({
            next: (userLogged: IUser | undefined) => {
              if (userLogged && userLogged.id === this.user?.id) {
                const updatedUser = { ...this.user, hobbies: updatedHobbies };
                this.userFacade.setUserData({ user: updatedUser });
              }
            },
            complete: () => {
              this.profileUpdatedEvent.emit();
            }
          });
        }
      });
    }
  }

  get getBtnValid(): boolean {
    if (this.userForm) {
      return this.userFormComponent?.form?.valid ?? false;
    } else {
      return this.userHobbyFormComponent?.isValid ?? false;
    }
  }
}
