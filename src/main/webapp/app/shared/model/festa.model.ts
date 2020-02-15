export interface IFesta {
  id?: number;
  name?: string;
}

export class Festa implements IFesta {
  constructor(public id?: number, public name?: string) {}
}
