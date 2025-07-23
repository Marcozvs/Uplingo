import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';

import { map, Observable } from 'rxjs';

import { environment } from '@environments/environment';
import { IResponse } from '@shared/interfaces/response.interfaces';
import { ICreateUserEnergy } from '@modules/user/interfaces/user.interfaces';

@Injectable({
  providedIn: 'root'
})
export class EnergyService {

  private readonly httpClient: HttpClient = inject(HttpClient);

  private readonly baseUrl = `${environment.resource.issuer}/api/energy`;

  request(): Observable<void> {
    return this.httpClient
      .post<IResponse<void>>(`${this.baseUrl}/request`, undefined)
      .pipe(
        map(res => {
          return res.data;
        })
      );
  }

  create(payload: ICreateUserEnergy): Observable<void> {
    return this.httpClient
      .post<IResponse<void>>(`${this.baseUrl}`, payload)
      .pipe(
        map(res => {
          return res.data;
        })
      );
  }
}
