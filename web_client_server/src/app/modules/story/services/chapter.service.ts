import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';

import { map, Observable } from 'rxjs';
import { v4 as uuidv4 } from 'uuid';
import { TranslateService } from '@ngx-translate/core';

import { environment } from '@environments/environment';
import { LayoutFacade } from '@layout/store/layout.facade';
import { ToastSeverityEnum } from '@shared/enum/toast.enums';
import { IResponse } from '@shared/interfaces/response.interfaces';
import {
  IChapter,
  IChapterCreate,
  IChapterUpdate,
  IChapterItem,
} from '@modules/story/interfaces/chapter.interfaces';
import { IStoryCheckAnswer } from '@modules/story/interfaces/story.interfaces';

@Injectable({
  providedIn: 'root',
})
export class ChapterService {
  private readonly httpClient: HttpClient = inject(HttpClient);
  private readonly layoutFacade: LayoutFacade = inject(LayoutFacade);
  private readonly translate: TranslateService = inject(TranslateService);

  private readonly baseUrl = `${environment.resource.issuer}/api/chapters`;

  read(id: string): Observable<IChapter> {
    return this.httpClient
      .get<IResponse<IChapter>>(`${this.baseUrl}/${id}`)
      .pipe(map(res => res.data));
  }

  readAll(): Observable<IChapterItem[]> {
    return this.httpClient
      .get<IResponse<IChapterItem[]>>(this.baseUrl)
      .pipe(map(res => res.data));
  }

  create(dto: IChapterCreate): Observable<IChapter> {
    return this.httpClient
      .post<IResponse<IChapter>>(this.baseUrl, dto)
      .pipe(
        map(res => {
          this.layoutFacade.addToast({
            id: uuidv4(),
            message: this.translate.instant('TOAST.CHAPTER.CREATED'),
            severity: ToastSeverityEnum.SUCCESS,
            displayed: false,
          });
          return res.data;
        })
      );
  }

  update(id: string, dto: IChapterUpdate): Observable<IChapter> {
    return this.httpClient
      .put<IResponse<IChapter>>(`${this.baseUrl}/${id}`, dto)
      .pipe(
        map(res => {
          this.layoutFacade.addToast({
            id: uuidv4(),
            message: this.translate.instant('TOAST.CHAPTER.UPDATED'),
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
            message: this.translate.instant('TOAST.CHAPTER.DELETED'),
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
