/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { KlavUserService } from 'app/entities/klav-user/klav-user.service';
import { IKlavUser, KlavUser } from 'app/shared/model/klav-user.model';

describe('Service Tests', () => {
    describe('KlavUser Service', () => {
        let injector: TestBed;
        let service: KlavUserService;
        let httpMock: HttpTestingController;
        let elemDefault: IKlavUser;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(KlavUserService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new KlavUser(0, 'AAAAAAA', currentDate, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA');
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        birthdate: currentDate.format(DATE_TIME_FORMAT)
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

            it('should create a KlavUser', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        birthdate: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        birthdate: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new KlavUser(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a KlavUser', async () => {
                const returnedFromService = Object.assign(
                    {
                        phoneNumber: 'BBBBBB',
                        birthdate: currentDate.format(DATE_TIME_FORMAT),
                        selfDescription: 'BBBBBB',
                        gender: 'BBBBBB',
                        nationality: 'BBBBBB'
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        birthdate: currentDate
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

            it('should return a list of KlavUser', async () => {
                const returnedFromService = Object.assign(
                    {
                        phoneNumber: 'BBBBBB',
                        birthdate: currentDate.format(DATE_TIME_FORMAT),
                        selfDescription: 'BBBBBB',
                        gender: 'BBBBBB',
                        nationality: 'BBBBBB'
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        birthdate: currentDate
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

            it('should delete a KlavUser', async () => {
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
