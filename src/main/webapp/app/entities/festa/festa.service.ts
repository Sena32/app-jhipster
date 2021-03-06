import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IFesta } from 'app/shared/model/festa.model';

type EntityResponseType = HttpResponse<IFesta>;
type EntityArrayResponseType = HttpResponse<IFesta[]>;

@Injectable({ providedIn: 'root' })
export class FestaService {
  public resourceUrl = SERVER_API_URL + 'api/festas';

  constructor(protected http: HttpClient) {}

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFesta>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFesta[]>(this.resourceUrl, { params: options, observe: 'response' });
  }
}
