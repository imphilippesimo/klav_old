import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITrustIndex } from 'app/shared/model/trust-index.model';
import { TrustIndexService } from './trust-index.service';

@Component({
    selector: 'jhi-trust-index-delete-dialog',
    templateUrl: './trust-index-delete-dialog.component.html'
})
export class TrustIndexDeleteDialogComponent {
    trustIndex: ITrustIndex;

    constructor(private trustIndexService: TrustIndexService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.trustIndexService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'trustIndexListModification',
                content: 'Deleted an trustIndex'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-trust-index-delete-popup',
    template: ''
})
export class TrustIndexDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ trustIndex }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(TrustIndexDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.trustIndex = trustIndex;
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
