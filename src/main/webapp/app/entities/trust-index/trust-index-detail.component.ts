import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITrustIndex } from 'app/shared/model/trust-index.model';

@Component({
    selector: 'jhi-trust-index-detail',
    templateUrl: './trust-index-detail.component.html'
})
export class TrustIndexDetailComponent implements OnInit {
    trustIndex: ITrustIndex;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ trustIndex }) => {
            this.trustIndex = trustIndex;
        });
    }

    previousState() {
        window.history.back();
    }
}
