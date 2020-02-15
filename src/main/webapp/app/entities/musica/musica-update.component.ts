import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IMusica, Musica } from 'app/shared/model/musica.model';
import { MusicaService } from './musica.service';
import { IPlaylist } from 'app/shared/model/playlist.model';
import { PlaylistService } from 'app/entities/playlist/playlist.service';

@Component({
  selector: 'jhi-musica-update',
  templateUrl: './musica-update.component.html'
})
export class MusicaUpdateComponent implements OnInit {
  isSaving = false;
  playlists: IPlaylist[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    track: [],
    playlist: []
  });

  constructor(
    protected musicaService: MusicaService,
    protected playlistService: PlaylistService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ musica }) => {
      this.updateForm(musica);

      this.playlistService.query().subscribe((res: HttpResponse<IPlaylist[]>) => (this.playlists = res.body || []));
    });
  }

  updateForm(musica: IMusica): void {
    this.editForm.patchValue({
      id: musica.id,
      name: musica.name,
      track: musica.track,
      playlist: musica.playlist
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const musica = this.createFromForm();
    if (musica.id !== undefined) {
      this.subscribeToSaveResponse(this.musicaService.update(musica));
    } else {
      this.subscribeToSaveResponse(this.musicaService.create(musica));
    }
  }

  private createFromForm(): IMusica {
    return {
      ...new Musica(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      track: this.editForm.get(['track'])!.value,
      playlist: this.editForm.get(['playlist'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMusica>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IPlaylist): any {
    return item.id;
  }
}
