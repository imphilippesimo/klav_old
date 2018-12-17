import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IKlavUser } from 'app/shared/model/klav-user.model';
import { Principal } from 'app/core';
import { KlavUserService } from './klav-user.service';

@Component({
    selector: 'jhi-klav-user',
    templateUrl: './klav-user.component.html'
})
export class KlavUserComponent implements OnInit, OnDestroy {
    klavUsers: IKlavUser[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private klavUserService: KlavUserService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.klavUserService.query().subscribe(
            (res: HttpResponse<IKlavUser[]>) => {
                this.klavUsers = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInKlavUsers();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IKlavUser) {
        return item.id;
    }

    registerChangeInKlavUsers() {
        this.eventSubscriber = this.eventManager.subscribe('klavUserListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
