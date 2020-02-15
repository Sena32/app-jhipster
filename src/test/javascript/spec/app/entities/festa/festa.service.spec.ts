import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { FestaService } from 'app/entities/festa/festa.service';
import { IFesta, Festa } from 'app/shared/model/festa.model';

describe('Service Tests', () => {
  describe('Festa Service', () => {
    let injector: TestBed;
    let service: FestaService;
    let httpMock: HttpTestingController;
    let elemDefault: IFesta;
    let expectedResult: IFesta | IFesta[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(FestaService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Festa(0, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should return a list of Festa', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
