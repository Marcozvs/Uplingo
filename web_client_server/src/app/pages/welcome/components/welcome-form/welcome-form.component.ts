import { CommonModule } from '@angular/common';
import { Component, inject, ViewChild } from '@angular/core';
import { Router } from '@angular/router';

import { TranslateModule } from '@ngx-translate/core';
import { catchError, forkJoin, of, take, tap } from 'rxjs';

import { UserHobbyFormComponent } from '@modules/user/components/user-hobby-form/user-hobby-form.component';
import { UserFacade } from '@modules/user/store/user.facade';
import { UserFormComponent } from '@modules/user/components/user-form/user-form.component';
import { StepperEnum } from '@shared/enum/stepper.enums';
import { StepperComponent } from '@shared/components/stepper/stepper.component';
import { ButtonComponent } from '@shared/components/button/button.component';
import { IStep } from '@shared/interfaces/stepper.interfaces';

@Component({
  standalone: true,
  selector: 'app-welcome-form',
  imports: [CommonModule, StepperComponent, UserFormComponent, UserHobbyFormComponent, ButtonComponent, TranslateModule],
  templateUrl: './welcome-form.component.html',
})
export class WelcomeFormComponent {
  @ViewChild(UserFormComponent) userFormComponent?: UserFormComponent;
  @ViewChild(UserHobbyFormComponent) userHobbyFormComponent?: UserHobbyFormComponent;

  private readonly router: Router = inject(Router);
  private readonly userFacade: UserFacade = inject(UserFacade);

  activeStep = 0;
  stepperMode = StepperEnum.ICON;
  steps: IStep[] = [
    { label: 'Step 1', icon: 'mdi mdi-account' },
    { label: 'Step 2', icon: 'mdi mdi-star-four-points' },
  ];

  handleStepChanged(stepIndex: number): void {
    this.activeStep = stepIndex;
  }

  handleNextStep(): void {
    if (this.activeStep < this.steps.length - 1) {
      this.activeStep++;
    }
  }

  handlePrevStep(): void {
    if (this.activeStep > 0) {
      this.activeStep--;
    }
  }

  handleConfirm(): void {
    if (!this.areBothFormsValid) return;

    forkJoin({
      user: this.userFormComponent!.submit(),
      hobby: this.userHobbyFormComponent!.submit()
    })
      .pipe(
        take(1),
        tap(({ user }) => {
          if (user) {
            this.userFacade.setUserData({ user: user });
          }
        }),
        catchError(error => {
          return of(null);
        })
      )
      .subscribe({
        complete: () => {
          this.router.navigate(['/story']);
        }
      })
  }

  get isUserFormValid(): boolean {
    return this.userFormComponent?.form?.valid ?? false;
  }

  get isHobbyFormValid(): boolean {
    return this.userHobbyFormComponent?.isValid ?? false;
  }

  get areBothFormsValid(): boolean {
    return this.isUserFormValid && this.isHobbyFormValid;
  }
}
