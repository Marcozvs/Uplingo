import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';

import { TranslateService } from '@ngx-translate/core';

import { v4 as uuidv4 } from 'uuid';

import { map, Observable } from 'rxjs';

import { environment } from '@environments/environment';

import { ToastSeverityEnum } from '@shared/enum/toast.enums';
import {
  IUserHobbyCreate,
  IUserHobbyUpdate,
  IUserHobby,
  IUserHobbyItem,
} from '@modules/user/interfaces/hobby.interfaces';
import { LayoutFacade } from '@layout/store/layout.facade';
import { IResponse } from '@shared/interfaces/response.interfaces';

@Injectable({
  providedIn: 'root',
})
export class HobbyService {
  private readonly httpClient: HttpClient = inject(HttpClient);
  private readonly layoutFacade: LayoutFacade = inject(LayoutFacade);
  private readonly translate: TranslateService = inject(TranslateService);

  private readonly baseUrl = `${environment.resource.issuer}/api/hobbies`;

  read(id: string): Observable<IUserHobby> {
    return this.httpClient
      .get<IResponse<IUserHobby>>(`${this.baseUrl}/${id}`)
      .pipe(map((res) => res.data));
  }

  readAll(): Observable<IUserHobbyItem[]> {
    return this.httpClient
      .get<IResponse<IUserHobbyItem[]>>(this.baseUrl)
      .pipe(map((res) => res.data));
  }

  create(dto: IUserHobbyCreate): Observable<IUserHobby> {
    return this.httpClient
      .post<IResponse<IUserHobby>>(this.baseUrl, dto)
      .pipe(
        map((res) => {
          this.layoutFacade.addToast({
            id: uuidv4(),
            message: this.translate.instant('TOAST.HOBBY.CREATED'),
            severity: ToastSeverityEnum.SUCCESS,
            displayed: false,
          });
          return res.data;
        })
      );
  }

  update(id: string, dto: IUserHobbyUpdate): Observable<IUserHobby> {
    return this.httpClient
      .put<IResponse<IUserHobby>>(`${this.baseUrl}/${id}`, dto)
      .pipe(
        map((res) => {
          this.layoutFacade.addToast({
            id: uuidv4(),
            message: this.translate.instant('TOAST.HOBBY.UPDATED'),
            severity: ToastSeverityEnum.SUCCESS,
            displayed: false,
          });
          return res.data;
        })
      );
  }

  delete(id: string): Observable<void> {
    return this.httpClient
      .delete<IResponse<void>>(`${this.baseUrl}/${id}`)
      .pipe(
        map((res) => {
          this.layoutFacade.addToast({
            id: uuidv4(),
            message: this.translate.instant('TOAST.HOBBY.DELETED'),
            severity: ToastSeverityEnum.SUCCESS,
            displayed: false,
          });
          return res.data;
        })
      );
  }
}
