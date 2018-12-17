import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IStatus } from 'app/shared/model/status.model';
import { Principal } from 'app/core';
import { StatusService } from './status.service';

@Component({
    selector: 'jhi-status',
    templateUrl: './status.component.html'
})
export class StatusComponent implements OnInit, OnDestroy {
    statuses: IStatus[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private statusService: StatusService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.statusService.query().subscribe(
            (res: HttpResponse<IStatus[]>) => {
                this.statuses = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInStatuses();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IStatus) {
        return item.id;
    }

    registerChangeInStatuses() {
        this.eventSubscriber = this.eventManager.subscribe('statusListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
