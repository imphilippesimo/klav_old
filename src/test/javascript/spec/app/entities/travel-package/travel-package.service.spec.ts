/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import { TravelPackageService } from 'app/entities/travel-package/travel-package.service';
import { ITravelPackage, TravelPackage, DeliveryMode } from 'app/shared/model/travel-package.model';

describe('Service Tests', () => {
    describe('TravelPackage Service', () => {
        let injector: TestBed;
        let service: TravelPackageService;
        let httpMock: HttpTestingController;
        let elemDefault: ITravelPackage;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(TravelPackageService);
            httpMock = injector.get(HttpTestingController);

            elemDefault = new TravelPackage(0, 'AAAAAAA', 0, 'AAAAAAA', 'AAAAAAA', DeliveryMode.HOMEDELEVERY, 'AAAAAAA', 0, false);
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign({}, elemDefault);
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a TravelPackage', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0
                    },
                    elemDefault
                );
                const expected = Object.assign({}, returnedFromService);
                service
                    .create(new TravelPackage(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a TravelPackage', async () => {
                const returnedFromService = Object.assign(
                    {
                        title: 'BBBBBB',
                        weight: 1,
                        accessCode: 'BBBBBB',
                        deleveryCode: 'BBBBBB',
                        desiredDeleveryMode: 'BBBBBB',
                        description: 'BBBBBB',
                        pricePerKG: 1,
                        fragile: true
                    },
                    elemDefault
                );

                const expected = Object.assign({}, returnedFromService);
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of TravelPackage', async () => {
                const returnedFromService = Object.assign(
                    {
                        title: 'BBBBBB',
                        weight: 1,
                        accessCode: 'BBBBBB',
                        deleveryCode: 'BBBBBB',
                        desiredDeleveryMode: 'BBBBBB',
                        description: 'BBBBBB',
                        pricePerKG: 1,
                        fragile: true
                    },
                    elemDefault
                );
                const expected = Object.assign({}, returnedFromService);
                service
                    .query(expected)
                    .pipe(take(1), map(resp => resp.body))
                    .subscribe(body => expect(body).toContainEqual(expected));
                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify([returnedFromService]));
                httpMock.verify();
            });

            it('should delete a TravelPackage', async () => {
                const rxPromise = service.delete(123).subscribe(resp => expect(resp.ok));

                const req = httpMock.expectOne({ method: 'DELETE' });
                req.flush({ status: 200 });
            });
        });

        afterEach(() => {
            httpMock.verify();
        });
    });
});
