import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMusica } from 'app/shared/model/musica.model';

@Component({
  selector: 'jhi-musica-detail',
  templateUrl: './musica-detail.component.html'
})
export class MusicaDetailComponent implements OnInit {
  musica: IMusica | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ musica }) => (this.musica = musica));
  }

  previousState(): void {
    window.history.back();
  }
}
