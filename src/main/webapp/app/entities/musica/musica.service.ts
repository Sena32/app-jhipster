import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IMusica } from 'app/shared/model/musica.model';

type EntityResponseType = HttpResponse<IMusica>;
type EntityArrayResponseType = HttpResponse<IMusica[]>;

@Injectable({ providedIn: 'root' })
export class MusicaService {
  public resourceUrl = SERVER_API_URL + 'api/musicas';

  constructor(protected http: HttpClient) {}

  create(musica: IMusica): Observable<EntityResponseType> {
    return this.http.post<IMusica>(this.resourceUrl, musica, { observe: 'response' });
  }

  update(musica: IMusica): Observable<EntityResponseType> {
    return this.http.put<IMusica>(this.resourceUrl, musica, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMusica>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMusica[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
