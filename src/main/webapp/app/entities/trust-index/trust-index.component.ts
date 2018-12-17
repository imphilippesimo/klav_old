import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ITrustIndex } from 'app/shared/model/trust-index.model';
import { Principal } from 'app/core';
import { TrustIndexService } from './trust-index.service';

@Component({
    selector: 'jhi-trust-index',
    templateUrl: './trust-index.component.html'
})
export class TrustIndexComponent implements OnInit, OnDestroy {
    trustIndices: ITrustIndex[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private trustIndexService: TrustIndexService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.trustIndexService.query().subscribe(
            (res: HttpResponse<ITrustIndex[]>) => {
                this.trustIndices = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInTrustIndices();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ITrustIndex) {
        return item.id;
    }

    registerChangeInTrustIndices() {
        this.eventSubscriber = this.eventManager.subscribe('trustIndexListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
