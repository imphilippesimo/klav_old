import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ITravelPackage } from 'app/shared/model/travel-package.model';
import { TravelPackageService } from './travel-package.service';
import { IKlavUser } from 'app/shared/model/klav-user.model';
import { KlavUserService } from 'app/entities/klav-user';
import { IBooking } from 'app/shared/model/booking.model';
import { BookingService } from 'app/entities/booking';

@Component({
    selector: 'jhi-travel-package-update',
    templateUrl: './travel-package-update.component.html'
})
export class TravelPackageUpdateComponent implements OnInit {
    travelPackage: ITravelPackage;
    isSaving: boolean;

    klavusers: IKlavUser[];

    receivers: IKlavUser[];

    bookings: IBooking[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private travelPackageService: TravelPackageService,
        private klavUserService: KlavUserService,
        private bookingService: BookingService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ travelPackage }) => {
            this.travelPackage = travelPackage;
        });
        this.klavUserService.query().subscribe(
            (res: HttpResponse<IKlavUser[]>) => {
                this.klavusers = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.klavUserService.query({ filter: 'travelpackage-is-null' }).subscribe(
            (res: HttpResponse<IKlavUser[]>) => {
                if (!this.travelPackage.receiver || !this.travelPackage.receiver.id) {
                    this.receivers = res.body;
                } else {
                    this.klavUserService.find(this.travelPackage.receiver.id).subscribe(
                        (subRes: HttpResponse<IKlavUser>) => {
                            this.receivers = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.bookingService.query().subscribe(
            (res: HttpResponse<IBooking[]>) => {
                this.bookings = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.travelPackage.id !== undefined) {
            this.subscribeToSaveResponse(this.travelPackageService.update(this.travelPackage));
        } else {
            this.subscribeToSaveResponse(this.travelPackageService.create(this.travelPackage));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ITravelPackage>>) {
        result.subscribe((res: HttpResponse<ITravelPackage>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackKlavUserById(index: number, item: IKlavUser) {
        return item.id;
    }

    trackBookingById(index: number, item: IBooking) {
        return item.id;
    }
}
