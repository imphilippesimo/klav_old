import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { KlavSharedModule } from 'app/shared';
import {
    TravelPackageComponent,
    TravelPackageDetailComponent,
    TravelPackageUpdateComponent,
    TravelPackageDeletePopupComponent,
    TravelPackageDeleteDialogComponent,
    travelPackageRoute,
    travelPackagePopupRoute
} from './';

const ENTITY_STATES = [...travelPackageRoute, ...travelPackagePopupRoute];

@NgModule({
    imports: [KlavSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        TravelPackageComponent,
        TravelPackageDetailComponent,
        TravelPackageUpdateComponent,
        TravelPackageDeleteDialogComponent,
        TravelPackageDeletePopupComponent
    ],
    entryComponents: [
        TravelPackageComponent,
        TravelPackageUpdateComponent,
        TravelPackageDeleteDialogComponent,
        TravelPackageDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class KlavTravelPackageModule {}
