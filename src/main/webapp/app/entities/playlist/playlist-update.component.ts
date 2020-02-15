import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IPlaylist, Playlist } from 'app/shared/model/playlist.model';
import { PlaylistService } from './playlist.service';

@Component({
  selector: 'jhi-playlist-update',
  templateUrl: './playlist-update.component.html'
})
export class PlaylistUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [],
    style: [],
    dtCreate: []
  });

  constructor(protected playlistService: PlaylistService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ playlist }) => {
      if (!playlist.id) {
        const today = moment().startOf('day');
        playlist.dtCreate = today;
      }

      this.updateForm(playlist);
    });
  }

  updateForm(playlist: IPlaylist): void {
    this.editForm.patchValue({
      id: playlist.id,
      name: playlist.name,
      style: playlist.style,
      dtCreate: playlist.dtCreate ? playlist.dtCreate.format(DATE_TIME_FORMAT) : null
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const playlist = this.createFromForm();
    if (playlist.id !== undefined) {
      this.subscribeToSaveResponse(this.playlistService.update(playlist));
    } else {
      this.subscribeToSaveResponse(this.playlistService.create(playlist));
    }
  }

  private createFromForm(): IPlaylist {
    return {
      ...new Playlist(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      style: this.editForm.get(['style'])!.value,
      dtCreate: this.editForm.get(['dtCreate'])!.value ? moment(this.editForm.get(['dtCreate'])!.value, DATE_TIME_FORMAT) : undefined
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPlaylist>>): void {
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
}
