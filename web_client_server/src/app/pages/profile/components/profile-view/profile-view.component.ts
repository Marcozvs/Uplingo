import { CommonModule } from '@angular/common';
import { Component, EventEmitter, inject, Input, Output } from '@angular/core';

import { map, Observable, take } from 'rxjs';
import { TranslateModule, TranslateService } from '@ngx-translate/core';
import { v4 as uuidv4 } from 'uuid';

import { IUser } from '@modules/user/interfaces/user.interfaces';
import { UserFacade } from '@modules/user/store/user.facade';
import { RoleEnum } from '@modules/user/enums/user.enums';
import { EnergyService } from '@modules/story/services/energy.service';
import { CardComponent } from '@shared/components/card/card.component';
import { LanguagePipe } from '@shared/pipes/language.pipe';
import { ButtonComponent } from '@shared/components/button/button.component';
import { ToastSeverityEnum } from '@shared/enum/toast.enums';
import { LayoutFacade } from '@layout/store/layout.facade';
import { ConfirmationModalComponent } from '@shared/components/confirmation/confirmation.component';
import { UserService } from '@modules/user/services/user.service';

@Component({
  standalone: true,
  selector: 'app-profile-view',
  imports: [
    CommonModule,
    CardComponent,
    ButtonComponent,
    ConfirmationModalComponent,
    LanguagePipe,
    TranslateModule
  ],
  templateUrl: './profile-view.component.html',
})
export class ProfileViewComponent {
  @Input({ required: true }) user: IUser | undefined;
  @Output() userFormVibilityEvent: EventEmitter<void> = new EventEmitter<void>();
  @Output() userHobbyFormVibilityEvent: EventEmitter<void> = new EventEmitter<void>();

  private readonly userFacade: UserFacade = inject(UserFacade);
  private readonly layoutFacade: LayoutFacade = inject(LayoutFacade);
  private readonly userService: UserService = inject(UserService);
  private readonly energyService: EnergyService = inject(EnergyService);
  private readonly translateService: TranslateService = inject(TranslateService);

  showConfirmBanUser: boolean = false;

  handleGiveEnergy(): void {
    if (this.user) {
      this.energyService.create({
        userId: this.user.id,
        validUntil: new Date(Date.now() + 7 * 24 * 60 * 60 * 1000).toISOString()
      }).pipe(take(1)).subscribe({
        complete: () => {
          this.layoutFacade.addToast({
            id: uuidv4(),
            message: this.translateService.instant('TOAST.REQUEST_ENERGY.ENERGY_GAVE'),
            severity: ToastSeverityEnum.SUCCESS,
            displayed: false,
          });
        }
      });
    }
  }

  handleBan(): void {
    this.showConfirmBanUser = true;
  }

  handleBanUser(): void {
    if (this.user)
      this.userService.ban(this.user.id).pipe(take(1)).subscribe({
        complete: () => {
          this.layoutFacade.addToast({
            id: uuidv4(),
            message: this.translateService.instant('TOAST.BAN_USER.USER_BANNED'),
            severity: ToastSeverityEnum.SUCCESS,
            displayed: false,
          });
        }
      })
  }

  get isUserLogged(): Observable<boolean> {
    return this.userFacade.user$.pipe(
      map((userLogged: IUser | undefined) => {
        if (!userLogged || !this.user) return false;
        return userLogged.id === this.user.id;
      })
    );
  }

  get isUserLoggedAdmin(): Observable<boolean> {
    return this.userFacade.user$.pipe(
      map((userLogged: IUser | undefined) => {
        if (!userLogged) return false;
        return userLogged.roles.includes(RoleEnum.ADMIN);
      })
    );
  }
}
