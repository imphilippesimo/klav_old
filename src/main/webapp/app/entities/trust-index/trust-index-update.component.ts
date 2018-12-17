import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { ITrustIndex } from 'app/shared/model/trust-index.model';
import { TrustIndexService } from './trust-index.service';
import { IBooking } from 'app/shared/model/booking.model';
import { BookingService } from 'app/entities/booking';

@Component({
    selector: 'jhi-trust-index-update',
    templateUrl: './trust-index-update.component.html'
})
export class TrustIndexUpdateComponent implements OnInit {
    trustIndex: ITrustIndex;
    isSaving: boolean;

    bookings: IBooking[];
    date: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private trustIndexService: TrustIndexService,
        private bookingService: BookingService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ trustIndex }) => {
            this.trustIndex = trustIndex;
            this.date = this.trustIndex.date != null ? this.trustIndex.date.format(DATE_TIME_FORMAT) : null;
        });
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
        this.trustIndex.date = this.date != null ? moment(this.date, DATE_TIME_FORMAT) : null;
        if (this.trustIndex.id !== undefined) {
            this.subscribeToSaveResponse(this.trustIndexService.update(this.trustIndex));
        } else {
            this.subscribeToSaveResponse(this.trustIndexService.create(this.trustIndex));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ITrustIndex>>) {
        result.subscribe((res: HttpResponse<ITrustIndex>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackBookingById(index: number, item: IBooking) {
        return item.id;
    }
}
