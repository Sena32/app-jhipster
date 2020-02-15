import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPlaylist } from 'app/shared/model/playlist.model';

type EntityResponseType = HttpResponse<IPlaylist>;
type EntityArrayResponseType = HttpResponse<IPlaylist[]>;

@Injectable({ providedIn: 'root' })
export class PlaylistService {
  public resourceUrl = SERVER_API_URL + 'api/playlists';

  constructor(protected http: HttpClient) {}

  create(playlist: IPlaylist): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(playlist);
    return this.http
      .post<IPlaylist>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(playlist: IPlaylist): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(playlist);
    return this.http
      .put<IPlaylist>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPlaylist>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPlaylist[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(playlist: IPlaylist): IPlaylist {
    const copy: IPlaylist = Object.assign({}, playlist, {
      dtCreate: playlist.dtCreate && playlist.dtCreate.isValid() ? playlist.dtCreate.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dtCreate = res.body.dtCreate ? moment(res.body.dtCreate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((playlist: IPlaylist) => {
        playlist.dtCreate = playlist.dtCreate ? moment(playlist.dtCreate) : undefined;
      });
    }
    return res;
  }
}
