import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { ITravel } from 'app/shared/model/travel.model';
import { TravelService } from './travel.service';
import { IKlavUser } from 'app/shared/model/klav-user.model';
import { KlavUserService } from 'app/entities/klav-user';

@Component({
    selector: 'jhi-travel-update',
    templateUrl: './travel-update.component.html'
})
export class TravelUpdateComponent implements OnInit {
    travel: ITravel;
    isSaving: boolean;

    travellers: IKlavUser[];
    departureDate: string;
    arrivalDate: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private travelService: TravelService,
        private klavUserService: KlavUserService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ travel }) => {
            this.travel = travel;
            this.departureDate = this.travel.departureDate != null ? this.travel.departureDate.format(DATE_TIME_FORMAT) : null;
            this.arrivalDate = this.travel.arrivalDate != null ? this.travel.arrivalDate.format(DATE_TIME_FORMAT) : null;
        });
        this.klavUserService.query({ filter: 'travel-is-null' }).subscribe(
            (res: HttpResponse<IKlavUser[]>) => {
                if (!this.travel.traveller || !this.travel.traveller.id) {
                    this.travellers = res.body;
                } else {
                    this.klavUserService.find(this.travel.traveller.id).subscribe(
                        (subRes: HttpResponse<IKlavUser>) => {
                            this.travellers = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.travel.departureDate = this.departureDate != null ? moment(this.departureDate, DATE_TIME_FORMAT) : null;
        this.travel.arrivalDate = this.arrivalDate != null ? moment(this.arrivalDate, DATE_TIME_FORMAT) : null;
        if (this.travel.id !== undefined) {
            this.subscribeToSaveResponse(this.travelService.update(this.travel));
        } else {
            this.subscribeToSaveResponse(this.travelService.create(this.travel));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ITravel>>) {
        result.subscribe((res: HttpResponse<ITravel>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
}
