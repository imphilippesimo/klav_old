import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Status } from 'app/shared/model/status.model';
import { StatusService } from './status.service';
import { StatusComponent } from './status.component';
import { StatusDetailComponent } from './status-detail.component';
import { StatusUpdateComponent } from './status-update.component';
import { StatusDeletePopupComponent } from './status-delete-dialog.component';
import { IStatus } from 'app/shared/model/status.model';

@Injectable({ providedIn: 'root' })
export class StatusResolve implements Resolve<IStatus> {
    constructor(private service: StatusService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((status: HttpResponse<Status>) => status.body));
        }
        return of(new Status());
    }
}

export const statusRoute: Routes = [
    {
        path: 'status',
        component: StatusComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'klavApp.status.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'status/:id/view',
        component: StatusDetailComponent,
        resolve: {
            status: StatusResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'klavApp.status.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'status/new',
        component: StatusUpdateComponent,
        resolve: {
            status: StatusResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'klavApp.status.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'status/:id/edit',
        component: StatusUpdateComponent,
        resolve: {
            status: StatusResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'klavApp.status.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const statusPopupRoute: Routes = [
    {
        path: 'status/:id/delete',
        component: StatusDeletePopupComponent,
        resolve: {
            status: StatusResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'klavApp.status.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
