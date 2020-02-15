import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IMusica, Musica } from 'app/shared/model/musica.model';
import { MusicaService } from './musica.service';
import { MusicaComponent } from './musica.component';
import { MusicaDetailComponent } from './musica-detail.component';
import { MusicaUpdateComponent } from './musica-update.component';

@Injectable({ providedIn: 'root' })
export class MusicaResolve implements Resolve<IMusica> {
  constructor(private service: MusicaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMusica> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((musica: HttpResponse<Musica>) => {
          if (musica.body) {
            return of(musica.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Musica());
  }
}

export const musicaRoute: Routes = [
  {
    path: '',
    component: MusicaComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'jhipsterApp.musica.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: MusicaDetailComponent,
    resolve: {
      musica: MusicaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipsterApp.musica.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: MusicaUpdateComponent,
    resolve: {
      musica: MusicaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipsterApp.musica.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: MusicaUpdateComponent,
    resolve: {
      musica: MusicaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipsterApp.musica.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
