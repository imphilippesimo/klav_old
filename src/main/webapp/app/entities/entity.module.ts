import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { KlavTravelModule } from './travel/travel.module';
import { KlavKlavUserModule } from './klav-user/klav-user.module';
import { KlavFileModule } from './file/file.module';
import { KlavAddressModule } from './address/address.module';
import { KlavTravelPackageModule } from './travel-package/travel-package.module';
import { KlavMessageModule } from './message/message.module';
import { KlavChatModule } from './chat/chat.module';
import { KlavStatusModule } from './status/status.module';
import { KlavTrustIndexModule } from './trust-index/trust-index.module';
import { KlavReviewModule } from './review/review.module';
import { KlavBookingModule } from './booking/booking.module';
import { KlavPackageTypeModule } from './package-type/package-type.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        KlavTravelModule,
        KlavKlavUserModule,
        KlavFileModule,
        KlavAddressModule,
        KlavTravelPackageModule,
        KlavMessageModule,
        KlavChatModule,
        KlavStatusModule,
        KlavTrustIndexModule,
        KlavReviewModule,
        KlavBookingModule,
        KlavPackageTypeModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class KlavEntityModule {}
