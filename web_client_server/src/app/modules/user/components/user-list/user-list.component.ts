import { CommonModule } from '@angular/common';
import { Component, inject, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormGroup } from '@angular/forms';

import { FormlyFieldConfig } from '@ngx-formly/core';
import { TranslateModule, TranslateService } from '@ngx-translate/core';
import { take } from 'rxjs';

import { IUserItem } from '@modules/user/interfaces/user.interfaces';
import { UserService } from '@modules/user/services/user.service';
import { defineUserListFormField } from '@modules/user/components/user-list/user-list-form-field-group';
import { CardComponent } from '@shared/components/card/card.component';
import { createFormGroup } from '@shared/utils/form.utils';
import { FormComponent } from '@shared/components/form/form.component';

@Component({
  standalone: true,
  selector: 'app-user-list',
  imports: [CommonModule, CardComponent, FormComponent, TranslateModule],
  templateUrl: './user-list.component.html'
})
export class UserListComponent implements OnInit {
  private readonly userService: UserService = inject(UserService);
  private readonly translateService: TranslateService = inject(TranslateService);
  private readonly router: Router = inject(Router);

  users: IUserItem[] = [];
  allUsers: IUserItem[] = [];
  model: { search: string | undefined } | undefined;
  form: FormGroup = new FormGroup({});
  formConfig: FormlyFieldConfig[] = [];

  ngOnInit(): void {
    this.defineUsers();
  }

  defineUsers(): void {
    this.userService.readAll().pipe(take(1)).subscribe({
      next: (users: IUserItem[]) => {
        this.allUsers = [...users];
        this.users = [...this.allUsers];
      },
      complete: () => {
        this.defineModel();
        this.defineForm();
        this.defineFormConfig();
      }
    });
  }

  private defineModel(): void {
    this.model = {
      search: undefined,
    };
  }

  private defineForm(): void {
    if (this.model)
      this.form = createFormGroup(this.model);
  }

  private defineFormConfig(): void {
    this.formConfig = [
      {
        fieldGroupClassName: 'flex flex-col gap-4 max-w-full',
        fieldGroup: defineUserListFormField({
          getModel: () => this.model,
          updateModel: (patch: Partial<{ search: string | undefined }>) => {
            this.model = {
              search: patch.search ?? this.model?.search ?? undefined
            };
          },
          translate: this.translateService,
          onSearchChange: () => this.handleSearchChange()
        }),
      },
    ];
  }

  handleSearchChange(): void {
    const term = this.model?.search?.toLowerCase()?.trim();

    if (!term) {
      this.users = [...this.allUsers];
      return;
    }

    this.users = this.allUsers.filter((user) =>
      user.name.toLowerCase().includes(term) ||
      user.email.toLowerCase().includes(term)
    );
  }

  handleViewUser(userId: string): void {
    this.router.navigate([`/profile/${userId}`]);
  }
}
