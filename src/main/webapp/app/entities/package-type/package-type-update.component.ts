import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IPackageType } from 'app/shared/model/package-type.model';
import { PackageTypeService } from './package-type.service';
import { ITravel } from 'app/shared/model/travel.model';
import { TravelService } from 'app/entities/travel';

@Component({
    selector: 'jhi-package-type-update',
    templateUrl: './package-type-update.component.html'
})
export class PackageTypeUpdateComponent implements OnInit {
    packageType: IPackageType;
    isSaving: boolean;

    travels: ITravel[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private packageTypeService: PackageTypeService,
        private travelService: TravelService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ packageType }) => {
            this.packageType = packageType;
        });
        this.travelService.query().subscribe(
            (res: HttpResponse<ITravel[]>) => {
                this.travels = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.packageType.id !== undefined) {
            this.subscribeToSaveResponse(this.packageTypeService.update(this.packageType));
        } else {
            this.subscribeToSaveResponse(this.packageTypeService.create(this.packageType));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IPackageType>>) {
        result.subscribe((res: HttpResponse<IPackageType>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackTravelById(index: number, item: ITravel) {
        return item.id;
    }
}
