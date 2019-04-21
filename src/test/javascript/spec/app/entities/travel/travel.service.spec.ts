/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { TravelService } from 'app/entities/travel/travel.service';
import { ITravel, Travel, TravelMode, DeliveryMode } from 'app/shared/model/travel.model';

describe('Service Tests', () => {
    describe('Travel Service', () => {
        let injector: TestBed;
        let service: TravelService;
        let httpMock: HttpTestingController;
        let elemDefault: ITravel;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(TravelService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new Travel(
                0,
                currentDate,
                currentDate,
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                0,
                0,
                TravelMode.PLANE,
                DeliveryMode.HOMEDELEVERY,
                'AAAAAAA',
                'AAAAAAA',
                false,
                'AAAAAAA'
            );
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        departureDate: currentDate.format(DATE_TIME_FORMAT),
                        arrivalDate: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a Travel', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        departureDate: currentDate.format(DATE_TIME_FORMAT),
                        arrivalDate: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        departureDate: currentDate,
                        arrivalDate: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new Travel(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Travel', async () => {
                const returnedFromService = Object.assign(
                    {
                        departureDate: currentDate.format(DATE_TIME_FORMAT),
                        arrivalDate: currentDate.format(DATE_TIME_FORMAT),
                        departureCountry: 'BBBBBB',
                        arrivalCountry: 'BBBBBB',
                        departureCity: 'BBBBBB',
                        arrivalCity: 'BBBBBB',
                        availableKGs: 1,
                        pricePerKG: 1,
                        travelMode: 'BBBBBB',
                        deleveryMode: 'BBBBBB',
                        howToContactDescription: 'BBBBBB',
                        complementaryRules: 'BBBBBB',
                        bookable: true,
                        accessCode: 'BBBBBB'
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        departureDate: currentDate,
                        arrivalDate: currentDate
                    },
                    returnedFromService
                );
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of Travel', async () => {
                const returnedFromService = Object.assign(
                    {
                        departureDate: currentDate.format(DATE_TIME_FORMAT),
                        arrivalDate: currentDate.format(DATE_TIME_FORMAT),
                        departureCountry: 'BBBBBB',
                        arrivalCountry: 'BBBBBB',
                        departureCity: 'BBBBBB',
                        arrivalCity: 'BBBBBB',
                        availableKGs: 1,
                        pricePerKG: 1,
                        travelMode: 'BBBBBB',
                        deleveryMode: 'BBBBBB',
                        howToContactDescription: 'BBBBBB',
                        complementaryRules: 'BBBBBB',
                        bookable: true,
                        accessCode: 'BBBBBB'
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        departureDate: currentDate,
                        arrivalDate: currentDate
                    },
                    returnedFromService
                );
                service
                    .query(expected)
                    .pipe(take(1), map(resp => resp.body))
                    .subscribe(body => expect(body).toContainEqual(expected));
                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify([returnedFromService]));
                httpMock.verify();
            });

            it('should delete a Travel', async () => {
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
