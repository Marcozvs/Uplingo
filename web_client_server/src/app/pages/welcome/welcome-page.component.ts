import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';

import { TranslateModule } from '@ngx-translate/core';

import { WelcomeFormComponent } from '@pages/welcome/components/welcome-form/welcome-form.component';


@Component({
  standalone: true,
  selector: 'app-welcome-page',
  imports: [CommonModule, WelcomeFormComponent, TranslateModule],
  templateUrl: './welcome-page.component.html',
})
export class WelcomePageComponent {}
