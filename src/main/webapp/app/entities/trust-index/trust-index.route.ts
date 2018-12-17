import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { TrustIndex } from 'app/shared/model/trust-index.model';
import { TrustIndexService } from './trust-index.service';
import { TrustIndexComponent } from './trust-index.component';
import { TrustIndexDetailComponent } from './trust-index-detail.component';
import { TrustIndexUpdateComponent } from './trust-index-update.component';
import { TrustIndexDeletePopupComponent } from './trust-index-delete-dialog.component';
import { ITrustIndex } from 'app/shared/model/trust-index.model';

@Injectable({ providedIn: 'root' })
export class TrustIndexResolve implements Resolve<ITrustIndex> {
    constructor(private service: TrustIndexService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((trustIndex: HttpResponse<TrustIndex>) => trustIndex.body));
        }
        return of(new TrustIndex());
    }
}

export const trustIndexRoute: Routes = [
    {
        path: 'trust-index',
        component: TrustIndexComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'klavApp.trustIndex.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'trust-index/:id/view',
        component: TrustIndexDetailComponent,
        resolve: {
            trustIndex: TrustIndexResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'klavApp.trustIndex.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'trust-index/new',
        component: TrustIndexUpdateComponent,
        resolve: {
            trustIndex: TrustIndexResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'klavApp.trustIndex.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'trust-index/:id/edit',
        component: TrustIndexUpdateComponent,
        resolve: {
            trustIndex: TrustIndexResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'klavApp.trustIndex.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const trustIndexPopupRoute: Routes = [
    {
        path: 'trust-index/:id/delete',
        component: TrustIndexDeletePopupComponent,
        resolve: {
            trustIndex: TrustIndexResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'klavApp.trustIndex.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
