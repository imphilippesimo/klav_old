import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ITravelPackage } from 'app/shared/model/travel-package.model';
import { Principal } from 'app/core';
import { TravelPackageService } from './travel-package.service';

@Component({
    selector: 'jhi-travel-package',
    templateUrl: './travel-package.component.html'
})
export class TravelPackageComponent implements OnInit, OnDestroy {
    travelPackages: ITravelPackage[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private travelPackageService: TravelPackageService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.travelPackageService.query().subscribe(
            (res: HttpResponse<ITravelPackage[]>) => {
                this.travelPackages = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInTravelPackages();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ITravelPackage) {
        return item.id;
    }

    registerChangeInTravelPackages() {
        this.eventSubscriber = this.eventManager.subscribe('travelPackageListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
