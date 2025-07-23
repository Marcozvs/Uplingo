import { Component, inject, OnInit } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { CommonModule } from '@angular/common';

import { initFlowbite } from 'flowbite';

import { StoryFacade } from '@modules/story/store/story.facade';
import { CourseFacade } from '@modules/course/store/course.facade';
import { HeaderComponent } from '@layout/components/header/header.component';
import { SidebarComponent } from '@layout/components/sidebar/sidebar.component';
import { FooterComponent } from '@layout/components/footer/footer.component';
import { BreadcrumbComponent } from '@layout/components/breadcrumb/breadcrumb.component';
import { LayoutService } from '@layout/services/layout.service';
import { LayoutFacade } from '@layout/store/layout.facade';
import { ToastComponent } from '@shared/components/toast/toast.component';
import { UserFacade } from '@modules/user/store/user.facade';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    CommonModule,
    RouterOutlet,
    HeaderComponent,
    ToastComponent,
    SidebarComponent,
    FooterComponent,
    BreadcrumbComponent
],
  templateUrl: './app.component.html',
})
export class AppComponent implements OnInit {
  private readonly layoutService: LayoutService = inject(LayoutService);
  protected readonly layoutFacade: LayoutFacade = inject(LayoutFacade);
  protected readonly userFacade: UserFacade = inject(UserFacade);

  protected toastQueue$ = this.layoutFacade.toastQueue$;

  ngOnInit(): void {
    initFlowbite();
    const storedTheme = this.layoutService.loadStoredTheme();
    if (storedTheme) {
      this.layoutService.applyTheme(storedTheme);
    }
  }
}
