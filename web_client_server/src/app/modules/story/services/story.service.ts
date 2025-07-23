import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';

import { map, Observable } from 'rxjs';
import { TranslateService } from '@ngx-translate/core';
import { v4 as uuidv4 } from 'uuid';

import { environment } from '@environments/environment';
import { LayoutFacade } from '@layout/store/layout.facade';
import { ToastSeverityEnum } from '@shared/enum/toast.enums';
import { IResponse } from '@shared/interfaces/response.interfaces';

import {
  IStory,
  IStoryCreate,
  IStoryUpdate,
  IStoryItem,
  IStoryCheckAnswer,
} from '@modules/story/interfaces/story.interfaces';

@Injectable({
  providedIn: 'root',
})
export class StoryService {
  private readonly httpClient: HttpClient = inject(HttpClient);
  private readonly layoutFacade: LayoutFacade = inject(LayoutFacade);
  private readonly translate: TranslateService = inject(TranslateService);

  private readonly baseUrl = `${environment.resource.issuer}/api/stories`;

  read(id: string): Observable<IStory> {
    return this.httpClient
      .get<IResponse<IStory>>(`${this.baseUrl}/${id}`)
      .pipe(map(res => res.data));
  }

  readAll(): Observable<IStoryItem[]> {
    return this.httpClient
      .get<IResponse<IStoryItem[]>>(this.baseUrl)
      .pipe(map(res => res.data));
  }

  create(dto: IStoryCreate): Observable<IStory> {
    return this.httpClient
      .post<IResponse<IStory>>(this.baseUrl, dto)
      .pipe(
        map(res => {
          this.layoutFacade.addToast({
            id: uuidv4(),
            message: this.translate.instant('TOAST.STORY.CREATED'),
            severity: ToastSeverityEnum.SUCCESS,
            displayed: false,
          });
          return res.data;
        })
      );
  }

  update(dto: IStoryUpdate): Observable<IStory> {
    return this.httpClient
      .put<IResponse<IStory>>(`${this.baseUrl}/${dto.id}`, dto)
      .pipe(
        map(res => {
          this.layoutFacade.addToast({
            id: uuidv4(),
            message: this.translate.instant('TOAST.STORY.UPDATED'),
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
            message: this.translate.instant('TOAST.STORY.DELETED'),
            severity: ToastSeverityEnum.SUCCESS,
            displayed: false,
          });
          return res.data;
        })
      );
  }

  checkAnswer(answerId: string): Observable<IStoryCheckAnswer> {
    return this.httpClient
      .post<IResponse<IStoryCheckAnswer>>(`${this.baseUrl}/check-answer/${answerId}`, {})
      .pipe(map(res => res.data));
  }
}
