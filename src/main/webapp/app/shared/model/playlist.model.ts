import { Moment } from 'moment';
import { IMusica } from 'app/shared/model/musica.model';

export interface IPlaylist {
  id?: number;
  name?: string;
  style?: string;
  dtCreate?: Moment;
  musicas?: IMusica[];
}

export class Playlist implements IPlaylist {
  constructor(public id?: number, public name?: string, public style?: string, public dtCreate?: Moment, public musicas?: IMusica[]) {}
}
