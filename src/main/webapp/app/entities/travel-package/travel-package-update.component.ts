import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ITravelPackage } from 'app/shared/model/travel-package.model';
import { TravelPackageService } from './travel-package.service';
import { IKlavUser } from 'app/shared/model/klav-user.model';
import { KlavUserService } from 'app/entities/klav-user';
import { IAddress } from 'app/shared/model/address.model';
import { AddressService } from 'app/entities/address';
import { IPackageType } from 'app/shared/model/package-type.model';
import { PackageTypeService } from 'app/entities/package-type';
import { IBooking } from 'app/shared/model/booking.model';
import { BookingService } from 'app/entities/booking';

@Component({
    selector: 'jhi-travel-package-update',
    templateUrl: './travel-package-update.component.html'
})
export class TravelPackageUpdateComponent implements OnInit {
    travelPackage: ITravelPackage;
    isSaving: boolean;

    receivers: IKlavUser[];

    destinationaddresses: IAddress[];

    types: IPackageType[];

    bookings: IBooking[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private travelPackageService: TravelPackageService,
        private klavUserService: KlavUserService,
        private addressService: AddressService,
        private packageTypeService: PackageTypeService,
        private bookingService: BookingService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ travelPackage }) => {
            this.travelPackage = travelPackage;
        });
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
        this.addressService.query({ filter: 'travelpackage-is-null' }).subscribe(
            (res: HttpResponse<IAddress[]>) => {
                if (!this.travelPackage.destinationAddress || !this.travelPackage.destinationAddress.id) {
                    this.destinationaddresses = res.body;
                } else {
                    this.addressService.find(this.travelPackage.destinationAddress.id).subscribe(
                        (subRes: HttpResponse<IAddress>) => {
                            this.destinationaddresses = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.packageTypeService.query({ filter: 'travelpackage-is-null' }).subscribe(
            (res: HttpResponse<IPackageType[]>) => {
                if (!this.travelPackage.type || !this.travelPackage.type.id) {
                    this.types = res.body;
                } else {
                    this.packageTypeService.find(this.travelPackage.type.id).subscribe(
                        (subRes: HttpResponse<IPackageType>) => {
                            this.types = [subRes.body].concat(res.body);
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

    trackAddressById(index: number, item: IAddress) {
        return item.id;
    }

    trackPackageTypeById(index: number, item: IPackageType) {
        return item.id;
    }

    trackBookingById(index: number, item: IBooking) {
        return item.id;
    }
}
