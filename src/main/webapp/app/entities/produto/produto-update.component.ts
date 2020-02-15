import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IProduto, Produto } from 'app/shared/model/produto.model';
import { ProdutoService } from './produto.service';

@Component({
  selector: 'jhi-produto-update',
  templateUrl: './produto-update.component.html'
})
export class ProdutoUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [],
    price: []
  });

  constructor(protected produtoService: ProdutoService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ produto }) => {
      this.updateForm(produto);
    });
  }

  updateForm(produto: IProduto): void {
    this.editForm.patchValue({
      id: produto.id,
      name: produto.name,
      price: produto.price
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const produto = this.createFromForm();
    if (produto.id !== undefined) {
      this.subscribeToSaveResponse(this.produtoService.update(produto));
    } else {
      this.subscribeToSaveResponse(this.produtoService.create(produto));
    }
  }

  private createFromForm(): IProduto {
    return {
      ...new Produto(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      price: this.editForm.get(['price'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProduto>>): void {
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
