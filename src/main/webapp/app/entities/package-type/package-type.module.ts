import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { KlavSharedModule } from 'app/shared';
import {
    PackageTypeComponent,
    PackageTypeDetailComponent,
    PackageTypeUpdateComponent,
    PackageTypeDeletePopupComponent,
    PackageTypeDeleteDialogComponent,
    packageTypeRoute,
    packageTypePopupRoute
} from './';

const ENTITY_STATES = [...packageTypeRoute, ...packageTypePopupRoute];

@NgModule({
    imports: [KlavSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PackageTypeComponent,
        PackageTypeDetailComponent,
        PackageTypeUpdateComponent,
        PackageTypeDeleteDialogComponent,
        PackageTypeDeletePopupComponent
    ],
    entryComponents: [PackageTypeComponent, PackageTypeUpdateComponent, PackageTypeDeleteDialogComponent, PackageTypeDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class KlavPackageTypeModule {}
