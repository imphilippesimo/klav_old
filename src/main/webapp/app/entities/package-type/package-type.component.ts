import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPackageType } from 'app/shared/model/package-type.model';
import { Principal } from 'app/core';
import { PackageTypeService } from './package-type.service';

@Component({
    selector: 'jhi-package-type',
    templateUrl: './package-type.component.html'
})
export class PackageTypeComponent implements OnInit, OnDestroy {
    packageTypes: IPackageType[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private packageTypeService: PackageTypeService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.packageTypeService.query().subscribe(
            (res: HttpResponse<IPackageType[]>) => {
                this.packageTypes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInPackageTypes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IPackageType) {
        return item.id;
    }

    registerChangeInPackageTypes() {
        this.eventSubscriber = this.eventManager.subscribe('packageTypeListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
