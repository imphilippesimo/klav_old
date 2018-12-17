import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { TravelPackage } from 'app/shared/model/travel-package.model';
import { TravelPackageService } from './travel-package.service';
import { TravelPackageComponent } from './travel-package.component';
import { TravelPackageDetailComponent } from './travel-package-detail.component';
import { TravelPackageUpdateComponent } from './travel-package-update.component';
import { TravelPackageDeletePopupComponent } from './travel-package-delete-dialog.component';
import { ITravelPackage } from 'app/shared/model/travel-package.model';

@Injectable({ providedIn: 'root' })
export class TravelPackageResolve implements Resolve<ITravelPackage> {
    constructor(private service: TravelPackageService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((travelPackage: HttpResponse<TravelPackage>) => travelPackage.body));
        }
        return of(new TravelPackage());
    }
}

export const travelPackageRoute: Routes = [
    {
        path: 'travel-package',
        component: TravelPackageComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'klavApp.travelPackage.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'travel-package/:id/view',
        component: TravelPackageDetailComponent,
        resolve: {
            travelPackage: TravelPackageResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'klavApp.travelPackage.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'travel-package/new',
        component: TravelPackageUpdateComponent,
        resolve: {
            travelPackage: TravelPackageResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'klavApp.travelPackage.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'travel-package/:id/edit',
        component: TravelPackageUpdateComponent,
        resolve: {
            travelPackage: TravelPackageResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'klavApp.travelPackage.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const travelPackagePopupRoute: Routes = [
    {
        path: 'travel-package/:id/delete',
        component: TravelPackageDeletePopupComponent,
        resolve: {
            travelPackage: TravelPackageResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'klavApp.travelPackage.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
