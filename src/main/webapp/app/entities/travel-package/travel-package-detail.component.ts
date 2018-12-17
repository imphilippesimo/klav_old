import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITravelPackage } from 'app/shared/model/travel-package.model';

@Component({
    selector: 'jhi-travel-package-detail',
    templateUrl: './travel-package-detail.component.html'
})
export class TravelPackageDetailComponent implements OnInit {
    travelPackage: ITravelPackage;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ travelPackage }) => {
            this.travelPackage = travelPackage;
        });
    }

    previousState() {
        window.history.back();
    }
}
