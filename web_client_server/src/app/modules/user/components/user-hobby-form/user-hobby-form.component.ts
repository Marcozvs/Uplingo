import { CommonModule } from '@angular/common';
import { Component, inject, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';

import { FormlyFieldConfig } from '@ngx-formly/core';
import { TranslateModule, TranslateService } from '@ngx-translate/core';
import { v4 as uuidv4 } from 'uuid';
import { catchError, forkJoin, map, Observable, of } from 'rxjs';

import { IUserHobbyCreate, IUserHobbyUpdate, IUserHobbyItem, IUserHobby } from '@modules/user/interfaces/hobby.interfaces';
import { defineUserHobbyFormField } from '@modules/user/components/user-hobby-form/user-hobby-form-field-group';
import { UserFacade } from '@modules/user/store/user.facade';
import { HobbyService } from '@modules/user/services/hobby.service';
import { createFormGroup } from '@shared/utils/form.utils';
import { ButtonComponent } from '@shared/components/button/button.component';
import { CardComponent } from '@shared/components/card/card.component';
import { ModalComponent } from '@shared/components/modal/modal.component';
import { FormComponent } from '@shared/components/form/form.component';

@Component({
  standalone: true,
  selector: 'app-user-hobby-form',
  imports: [
    CommonModule,
    ButtonComponent,
    CardComponent,
    ModalComponent,
    FormComponent,
    TranslateModule,
  ],
  templateUrl: './user-hobby-form.component.html',
})
export class UserHobbyFormComponent implements OnInit {
  private readonly userFacade = inject(UserFacade);
  private readonly translateService = inject(TranslateService);
  private readonly hobbyService = inject(HobbyService);

  model: IUserHobbyCreate | IUserHobbyUpdate = {};
  form!: FormGroup;
  formConfig: FormlyFieldConfig[] = [];
  hobbies: IUserHobbyItem[] = [];
  originalHobbies: IUserHobbyItem[] = [];
  showModal = false;
  editingIndex: number | null = null;
  menuIndex: number | null = null;
  userId?: string;

  ngOnInit(): void {
    this.userFacade.user$.subscribe(user => {
      if (!user) return;
      this.userId = user.id;
      this.hobbies = [...user.hobbies];
      this.originalHobbies = [...user.hobbies];
      this.defineModel();
    });
  }

  private defineModel(): void {
    this.model = { userId: this.userId, description: '' };
    this.defineForm();
    this.defineFormConfig();
  }

  private defineForm(): void {
    this.form = createFormGroup(this.model);
  }

  private defineFormConfig(): void {
    this.formConfig = [
      {
        fieldGroupClassName: 'flex flex-col gap-4 max-w-full',
        fieldGroup: defineUserHobbyFormField({
          getModel: () => this.model,
          updateModel: patch => (this.model = { ...this.model, ...patch }),
          translate: this.translateService,
        }),
      },
    ];
  }

  handleAddHobby(): void {
    this.model = { userId: this.userId, description: '' };
    this.defineForm();
    this.editingIndex = null;
    this.menuIndex = null;
    this.showModal = true;
  }

  handleSaveHobby(): void {
    const description = this.model.description?.trim();
    if (!description || !this.userId) return;
    if (this.hobbies.length >= 5 && !('id' in this.model)) return;

    const isUpdate = (m: IUserHobbyCreate | IUserHobbyUpdate): m is IUserHobbyUpdate & { id: string } =>
      'id' in m && typeof m.id === 'string' && m.id.length > 0;

    if (isUpdate(this.model)) {
      const modelWithId = this.model;

      const index = this.hobbies.findIndex(h => h.id === modelWithId.id);
      if (index === -1) return;

      this.hobbies[index] = {
        id: modelWithId.id,
        userId: this.userId,
        description,
      };
    } else {
      this.hobbies.push({
        id: uuidv4(),
        userId: this.userId,
        description,
      });
    }

    this.handleCloseModal();
  }

  handleEditHobby(index: number): void {
    const hobby = this.hobbies[index];
    this.model = { id: hobby.id, userId: hobby.userId, description: hobby.description };
    this.defineForm();
    this.editingIndex = index;
    this.menuIndex = null;
    this.showModal = true;
  }

  handleDeleteHobby(index: number): void {
    this.hobbies.splice(index, 1);
    this.menuIndex = null;
  }

  handleOpenMenu(index: number): void {
    this.menuIndex = this.menuIndex === index ? null : index;
  }

  handleCloseMenu(): void {
    this.menuIndex = null;
  }

  handleCloseModal(): void {
    this.showModal = false;
    this.model = {};
    this.editingIndex = null;
    this.menuIndex = null;
  }

  submit(): Observable<IUserHobbyItem[]> {
    if (!this.userId) {
      return of([]);
    }

    const created = this.hobbies.filter(h =>
      !h.id || !this.originalHobbies.some(o => o.id === h.id)
    );

    const updated = this.hobbies.filter(h => {
      if (!h.id) return false;
      const original = this.originalHobbies.find(o => o.id === h.id);
      return original && original.description !== h.description;
    });

    const deleted = this.originalHobbies.filter(o =>
      !this.hobbies.some(h => h.id === o.id)
    );

    const createRequests = created.map(hobby => {
      const dto: IUserHobbyCreate = {
        userId: this.userId!,
        description: hobby.description,
      };
      return this.hobbyService.create(dto).pipe(
        catchError(error => {
          return of(null);
        })
      );
    });

    const updateRequests = updated.map(hobby => {
      const dto: IUserHobbyUpdate = {
        id: hobby.id,
        description: hobby.description,
        updatedBy: this.userId!,
      };
      return this.hobbyService.update(hobby.id!, dto).pipe(
        catchError(error => {
          return of(null);
        })
      );
    });

    const deleteRequests = deleted.map(hobby =>
      this.hobbyService.delete(hobby.id).pipe(
        catchError(error => {
          return of(null);
        })
      )
    );

    const allRequests = [...createRequests, ...updateRequests, ...deleteRequests];

    return forkJoin(allRequests).pipe(
      map(results => {
        const createdAndUpdated = results.filter((res): res is IUserHobby => !!res);

        const keptHobbies = this.originalHobbies.filter(orig =>
          !deleted.some(del => del.id === orig.id) &&
          !updated.some(upd => upd.id === orig.id)
        );

        const finalHobbies: IUserHobbyItem[] = [
          ...keptHobbies,
          ...createdAndUpdated.map(h => ({
            id: h.id,
            userId: this.userId!,
            description: h.description,
          })),
        ];

        this.originalHobbies = finalHobbies;
        this.hobbies = [...finalHobbies];

        return finalHobbies;
      })
    );
  }

  get isValid(): boolean {
    return this.hobbies.length > 0;
  }
}
