import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { PackageType } from 'app/shared/model/package-type.model';
import { PackageTypeService } from './package-type.service';
import { PackageTypeComponent } from './package-type.component';
import { PackageTypeDetailComponent } from './package-type-detail.component';
import { PackageTypeUpdateComponent } from './package-type-update.component';
import { PackageTypeDeletePopupComponent } from './package-type-delete-dialog.component';
import { IPackageType } from 'app/shared/model/package-type.model';

@Injectable({ providedIn: 'root' })
export class PackageTypeResolve implements Resolve<IPackageType> {
    constructor(private service: PackageTypeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((packageType: HttpResponse<PackageType>) => packageType.body));
        }
        return of(new PackageType());
    }
}

export const packageTypeRoute: Routes = [
    {
        path: 'package-type',
        component: PackageTypeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'klavApp.packageType.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'package-type/:id/view',
        component: PackageTypeDetailComponent,
        resolve: {
            packageType: PackageTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'klavApp.packageType.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'package-type/new',
        component: PackageTypeUpdateComponent,
        resolve: {
            packageType: PackageTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'klavApp.packageType.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'package-type/:id/edit',
        component: PackageTypeUpdateComponent,
        resolve: {
            packageType: PackageTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'klavApp.packageType.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const packageTypePopupRoute: Routes = [
    {
        path: 'package-type/:id/delete',
        component: PackageTypeDeletePopupComponent,
        resolve: {
            packageType: PackageTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'klavApp.packageType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
