export interface IProduto {
  id?: number;
  name?: string;
  price?: number;
}

export class Produto implements IProduto {
  constructor(public id?: number, public name?: string, public price?: number) {}
}
