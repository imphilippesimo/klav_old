import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IKlavUser } from 'app/shared/model/klav-user.model';
import { KlavUserService } from './klav-user.service';

@Component({
    selector: 'jhi-klav-user-delete-dialog',
    templateUrl: './klav-user-delete-dialog.component.html'
})
export class KlavUserDeleteDialogComponent {
    klavUser: IKlavUser;

    constructor(private klavUserService: KlavUserService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.klavUserService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'klavUserListModification',
                content: 'Deleted an klavUser'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-klav-user-delete-popup',
    template: ''
})
export class KlavUserDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ klavUser }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(KlavUserDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.klavUser = klavUser;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
