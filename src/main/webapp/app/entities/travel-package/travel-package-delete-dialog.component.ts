import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITravelPackage } from 'app/shared/model/travel-package.model';
import { TravelPackageService } from './travel-package.service';

@Component({
    selector: 'jhi-travel-package-delete-dialog',
    templateUrl: './travel-package-delete-dialog.component.html'
})
export class TravelPackageDeleteDialogComponent {
    travelPackage: ITravelPackage;

    constructor(
        private travelPackageService: TravelPackageService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.travelPackageService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'travelPackageListModification',
                content: 'Deleted an travelPackage'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-travel-package-delete-popup',
    template: ''
})
export class TravelPackageDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ travelPackage }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(TravelPackageDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.travelPackage = travelPackage;
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
