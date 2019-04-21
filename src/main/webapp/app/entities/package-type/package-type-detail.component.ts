import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPackageType } from 'app/shared/model/package-type.model';

@Component({
    selector: 'jhi-package-type-detail',
    templateUrl: './package-type-detail.component.html'
})
export class PackageTypeDetailComponent implements OnInit {
    packageType: IPackageType;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ packageType }) => {
            this.packageType = packageType;
        });
    }

    previousState() {
        window.history.back();
    }
}
