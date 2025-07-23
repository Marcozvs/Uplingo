import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { UserListComponent } from '@modules/user/components/user-list/user-list.component';

@Component({
  selector: 'app-user-management-page',
  imports: [CommonModule, UserListComponent],
  templateUrl: './user-management-page.component.html'
})
export class UserManagementPageComponent {}
