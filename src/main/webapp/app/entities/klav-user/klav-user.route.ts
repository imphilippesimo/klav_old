import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { KlavUser } from 'app/shared/model/klav-user.model';
import { KlavUserService } from './klav-user.service';
import { KlavUserComponent } from './klav-user.component';
import { KlavUserDetailComponent } from './klav-user-detail.component';
import { KlavUserUpdateComponent } from './klav-user-update.component';
import { KlavUserDeletePopupComponent } from './klav-user-delete-dialog.component';
import { IKlavUser } from 'app/shared/model/klav-user.model';

@Injectable({ providedIn: 'root' })
export class KlavUserResolve implements Resolve<IKlavUser> {
    constructor(private service: KlavUserService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((klavUser: HttpResponse<KlavUser>) => klavUser.body));
        }
        return of(new KlavUser());
    }
}

export const klavUserRoute: Routes = [
    {
        path: 'klav-user',
        component: KlavUserComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'klavApp.klavUser.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'klav-user/:id/view',
        component: KlavUserDetailComponent,
        resolve: {
            klavUser: KlavUserResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'klavApp.klavUser.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'klav-user/new',
        component: KlavUserUpdateComponent,
        resolve: {
            klavUser: KlavUserResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'klavApp.klavUser.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'klav-user/:id/edit',
        component: KlavUserUpdateComponent,
        resolve: {
            klavUser: KlavUserResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'klavApp.klavUser.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const klavUserPopupRoute: Routes = [
    {
        path: 'klav-user/:id/delete',
        component: KlavUserDeletePopupComponent,
        resolve: {
            klavUser: KlavUserResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'klavApp.klavUser.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
