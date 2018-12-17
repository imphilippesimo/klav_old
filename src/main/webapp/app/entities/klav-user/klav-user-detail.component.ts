import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IKlavUser } from 'app/shared/model/klav-user.model';

@Component({
    selector: 'jhi-klav-user-detail',
    templateUrl: './klav-user-detail.component.html'
})
export class KlavUserDetailComponent implements OnInit {
    klavUser: IKlavUser;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ klavUser }) => {
            this.klavUser = klavUser;
        });
    }

    previousState() {
        window.history.back();
    }
}
