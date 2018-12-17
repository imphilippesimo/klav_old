import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IFile } from 'app/shared/model/file.model';
import { FileService } from './file.service';
import { ITravel } from 'app/shared/model/travel.model';
import { TravelService } from 'app/entities/travel';
import { IKlavUser } from 'app/shared/model/klav-user.model';
import { KlavUserService } from 'app/entities/klav-user';
import { ITravelPackage } from 'app/shared/model/travel-package.model';
import { TravelPackageService } from 'app/entities/travel-package';

@Component({
    selector: 'jhi-file-update',
    templateUrl: './file-update.component.html'
})
export class FileUpdateComponent implements OnInit {
    file: IFile;
    isSaving: boolean;

    travels: ITravel[];

    klavusers: IKlavUser[];

    travelpackages: ITravelPackage[];
    updatedDate: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private fileService: FileService,
        private travelService: TravelService,
        private klavUserService: KlavUserService,
        private travelPackageService: TravelPackageService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ file }) => {
            this.file = file;
            this.updatedDate = this.file.updatedDate != null ? this.file.updatedDate.format(DATE_TIME_FORMAT) : null;
        });
        this.travelService.query().subscribe(
            (res: HttpResponse<ITravel[]>) => {
                this.travels = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.klavUserService.query().subscribe(
            (res: HttpResponse<IKlavUser[]>) => {
                this.klavusers = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.travelPackageService.query().subscribe(
            (res: HttpResponse<ITravelPackage[]>) => {
                this.travelpackages = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.file.updatedDate = this.updatedDate != null ? moment(this.updatedDate, DATE_TIME_FORMAT) : null;
        if (this.file.id !== undefined) {
            this.subscribeToSaveResponse(this.fileService.update(this.file));
        } else {
            this.subscribeToSaveResponse(this.fileService.create(this.file));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IFile>>) {
        result.subscribe((res: HttpResponse<IFile>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackKlavUserById(index: number, item: IKlavUser) {
        return item.id;
    }

    trackTravelPackageById(index: number, item: ITravelPackage) {
        return item.id;
    }
}
