import { IPlaylist } from 'app/shared/model/playlist.model';

export interface IMusica {
  id?: number;
  name?: string;
  track?: number;
  playlist?: IPlaylist;
}

export class Musica implements IMusica {
  constructor(public id?: number, public name?: string, public track?: number, public playlist?: IPlaylist) {}
}
