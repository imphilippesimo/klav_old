import { IKlavUser } from 'app/shared/model//klav-user.model';
import { ITravelPackage } from 'app/shared/model//travel-package.model';
import { IStatus } from 'app/shared/model//status.model';
import { ITrustIndex } from 'app/shared/model//trust-index.model';

export interface IBooking {
    id?: number;
    klavUser?: IKlavUser;
    packages?: ITravelPackage[];
    statuses?: IStatus[];
    trustIndices?: ITrustIndex[];
}

export class Booking implements IBooking {
    constructor(
        public id?: number,
        public klavUser?: IKlavUser,
        public packages?: ITravelPackage[],
        public statuses?: IStatus[],
        public trustIndices?: ITrustIndex[]
    ) {}
}
