import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'produto',
        loadChildren: () => import('./produto/produto.module').then(m => m.JhipsterProdutoModule)
      },
      {
        path: 'festa',
        loadChildren: () => import('./festa/festa.module').then(m => m.JhipsterFestaModule)
      },
      {
        path: 'playlist',
        loadChildren: () => import('./playlist/playlist.module').then(m => m.JhipsterPlaylistModule)
      },
      {
        path: 'musica',
        loadChildren: () => import('./musica/musica.module').then(m => m.JhipsterMusicaModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class JhipsterEntityModule {}
