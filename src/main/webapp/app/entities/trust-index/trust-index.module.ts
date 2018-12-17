import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { KlavSharedModule } from 'app/shared';
import {
    TrustIndexComponent,
    TrustIndexDetailComponent,
    TrustIndexUpdateComponent,
    TrustIndexDeletePopupComponent,
    TrustIndexDeleteDialogComponent,
    trustIndexRoute,
    trustIndexPopupRoute
} from './';

const ENTITY_STATES = [...trustIndexRoute, ...trustIndexPopupRoute];

@NgModule({
    imports: [KlavSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        TrustIndexComponent,
        TrustIndexDetailComponent,
        TrustIndexUpdateComponent,
        TrustIndexDeleteDialogComponent,
        TrustIndexDeletePopupComponent
    ],
    entryComponents: [TrustIndexComponent, TrustIndexUpdateComponent, TrustIndexDeleteDialogComponent, TrustIndexDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class KlavTrustIndexModule {}
