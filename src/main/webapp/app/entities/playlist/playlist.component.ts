import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPlaylist } from 'app/shared/model/playlist.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { PlaylistService } from './playlist.service';
import { PlaylistDeleteDialogComponent } from './playlist-delete-dialog.component';

@Component({
  selector: 'jhi-playlist',
  templateUrl: './playlist.component.html'
})
export class PlaylistComponent implements OnInit, OnDestroy {
  playlists: IPlaylist[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected playlistService: PlaylistService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.playlists = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.playlistService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IPlaylist[]>) => this.paginatePlaylists(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.playlists = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPlaylists();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPlaylist): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInPlaylists(): void {
    this.eventSubscriber = this.eventManager.subscribe('playlistListModification', () => this.reset());
  }

  delete(playlist: IPlaylist): void {
    const modalRef = this.modalService.open(PlaylistDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.playlist = playlist;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginatePlaylists(data: IPlaylist[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.playlists.push(data[i]);
      }
    }
  }
}
