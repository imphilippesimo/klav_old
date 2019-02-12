import { Moment } from 'moment';
import { IKlavUser } from 'app/shared/model//klav-user.model';
import { IPackageType } from 'app/shared/model//package-type.model';
import { IFile } from 'app/shared/model//file.model';
import { IBooking } from 'app/shared/model//booking.model';

export const enum TravelMode {
    PLANE = 'PLANE',
    BOAT = 'BOAT'
}

export const enum DeliveryMode {
    HOMEDELEVERY = 'HOMEDELEVERY',
    HOMEWITHDRAWAL = 'HOMEWITHDRAWAL'
}

export interface ITravel {
    id?: number;
    departureDate?: Moment;
    arrivalDate?: Moment;
    departureCountry?: string;
    arrivalCountry?: string;
    departureCity?: string;
    arrivalCity?: string;
    availableKGs?: number;
    pricePerKG?: number;
    travelMode?: TravelMode;
    deleveryMode?: DeliveryMode;
    howToContactDescription?: string;
    complementaryRules?: string;
    bookable?: boolean;
    accessCode?: string;
    traveller?: IKlavUser;
    acceptedPackageTypes?: IPackageType[];
    travelProofs?: IFile[];
    bookings?: IBooking[];
}

export class Travel implements ITravel {
    constructor(
        public id?: number,
        public departureDate?: Moment,
        public arrivalDate?: Moment,
        public departureCountry?: string,
        public arrivalCountry?: string,
        public departureCity?: string,
        public arrivalCity?: string,
        public availableKGs?: number,
        public pricePerKG?: number,
        public travelMode?: TravelMode,
        public deleveryMode?: DeliveryMode,
        public howToContactDescription?: string,
        public complementaryRules?: string,
        public bookable?: boolean,
        public accessCode?: string,
        public traveller?: IKlavUser,
        public acceptedPackageTypes?: IPackageType[],
        public travelProofs?: IFile[],
        public bookings?: IBooking[]
    ) {
        this.bookable = this.bookable || false;
    }
}
