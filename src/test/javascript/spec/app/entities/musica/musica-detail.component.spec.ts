import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterTestModule } from '../../../test.module';
import { MusicaDetailComponent } from 'app/entities/musica/musica-detail.component';
import { Musica } from 'app/shared/model/musica.model';

describe('Component Tests', () => {
  describe('Musica Management Detail Component', () => {
    let comp: MusicaDetailComponent;
    let fixture: ComponentFixture<MusicaDetailComponent>;
    const route = ({ data: of({ musica: new Musica(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterTestModule],
        declarations: [MusicaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(MusicaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MusicaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load musica on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.musica).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
