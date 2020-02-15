import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSharedModule } from 'app/shared/shared.module';
import { MusicaComponent } from './musica.component';
import { MusicaDetailComponent } from './musica-detail.component';
import { MusicaUpdateComponent } from './musica-update.component';
import { MusicaDeleteDialogComponent } from './musica-delete-dialog.component';
import { musicaRoute } from './musica.route';

@NgModule({
  imports: [JhipsterSharedModule, RouterModule.forChild(musicaRoute)],
  declarations: [MusicaComponent, MusicaDetailComponent, MusicaUpdateComponent, MusicaDeleteDialogComponent],
  entryComponents: [MusicaDeleteDialogComponent]
})
export class JhipsterMusicaModule {}
