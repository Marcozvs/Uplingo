import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';

import { map, Observable } from 'rxjs';
import { TranslateService } from '@ngx-translate/core';
import { v4 as uuidv4 } from 'uuid';
import { environment } from '@environments/environment';

import { IUser, IUserItem, IUserUpdate } from '@modules/user/interfaces/user.interfaces';
import { LayoutFacade } from '@layout/store/layout.facade';
import { ToastSeverityEnum } from '@shared/enum/toast.enums';
import { IResponse } from '@shared/interfaces/response.interfaces';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private readonly httpClient: HttpClient = inject(HttpClient);
  private readonly layoutFacade: LayoutFacade = inject(LayoutFacade);
  private readonly translate: TranslateService = inject(TranslateService);

  private readonly baseUrl = `${environment.resource.issuer}/api/users`;

  read(id: string): Observable<IUser> {
    return this.httpClient
      .get<IResponse<IUser>>(`${this.baseUrl}/${id}`)
      .pipe(map(res => res.data));
  }

  readAll(): Observable<IUserItem[]> {
    return this.httpClient
      .get<IResponse<IUserItem[]>>(this.baseUrl)
      .pipe(map(res => res.data));
  }

  update(dto: IUserUpdate): Observable<IUser> {
    return this.httpClient
      .put<IResponse<IUser>>(`${this.baseUrl}/${dto.id}`, dto)
      .pipe(
        map(res => {
          this.layoutFacade.addToast({
            id: uuidv4(),
            message: this.translate.instant('TOAST.USER.UPDATED'),
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
        map(res => {
          this.layoutFacade.addToast({
            id: uuidv4(),
            message: this.translate.instant('TOAST.USER.DELETED'),
            severity: ToastSeverityEnum.SUCCESS,
            displayed: false,
          });
          return res.data;
        })
      );
  }

  ban(id: string): Observable<void> {
    return this.httpClient
      .put<IResponse<void>>(`${this.baseUrl}/${id}/ban`, {})
      .pipe(
        map(res => {
          this.layoutFacade.addToast({
            id: uuidv4(),
            message: this.translate.instant('TOAST.USER.BANNED'),
            severity: ToastSeverityEnum.SUCCESS,
            displayed: false,
          });
          return res.data;
        })
      );
  }
}
