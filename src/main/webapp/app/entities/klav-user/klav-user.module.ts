import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { KlavSharedModule } from 'app/shared';
import { KlavAdminModule } from 'app/admin/admin.module';
import {
    KlavUserComponent,
    KlavUserDetailComponent,
    KlavUserUpdateComponent,
    KlavUserDeletePopupComponent,
    KlavUserDeleteDialogComponent,
    klavUserRoute,
    klavUserPopupRoute
} from './';

const ENTITY_STATES = [...klavUserRoute, ...klavUserPopupRoute];

@NgModule({
    imports: [KlavSharedModule, KlavAdminModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        KlavUserComponent,
        KlavUserDetailComponent,
        KlavUserUpdateComponent,
        KlavUserDeleteDialogComponent,
        KlavUserDeletePopupComponent
    ],
    entryComponents: [KlavUserComponent, KlavUserUpdateComponent, KlavUserDeleteDialogComponent, KlavUserDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class KlavKlavUserModule {}
