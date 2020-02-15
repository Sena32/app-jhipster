import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMusica } from 'app/shared/model/musica.model';
import { MusicaService } from './musica.service';

@Component({
  templateUrl: './musica-delete-dialog.component.html'
})
export class MusicaDeleteDialogComponent {
  musica?: IMusica;

  constructor(protected musicaService: MusicaService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.musicaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('musicaListModification');
      this.activeModal.close();
    });
  }
}
