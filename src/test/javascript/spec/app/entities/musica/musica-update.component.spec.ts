import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterTestModule } from '../../../test.module';
import { MusicaUpdateComponent } from 'app/entities/musica/musica-update.component';
import { MusicaService } from 'app/entities/musica/musica.service';
import { Musica } from 'app/shared/model/musica.model';

describe('Component Tests', () => {
  describe('Musica Management Update Component', () => {
    let comp: MusicaUpdateComponent;
    let fixture: ComponentFixture<MusicaUpdateComponent>;
    let service: MusicaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterTestModule],
        declarations: [MusicaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(MusicaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MusicaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MusicaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Musica(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Musica();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
