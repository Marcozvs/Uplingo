import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';

import { map, Observable } from 'rxjs';
import { environment } from '@environments/environment';

import { IResponse } from '@shared/interfaces/response.interfaces';

import { ICourse, ICourseItem } from '@modules/course/interfaces/course.interfaces';

@Injectable({
  providedIn: 'root',
})
export class CourseService {
  private readonly httpClient: HttpClient = inject(HttpClient);

  private readonly baseUrl = `${environment.resource.issuer}/api/courses`;

  read(id: string): Observable<ICourse> {
    return this.httpClient
      .get<IResponse<ICourse>>(`${this.baseUrl}/${id}`)
      .pipe(map(res => res.data));
  }

  readAll(): Observable<ICourseItem[]> {
    return this.httpClient
      .get<IResponse<ICourseItem[]>>(this.baseUrl)
      .pipe(map(res => res.data));
  }
}
