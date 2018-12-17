import { Moment } from 'moment';
import { IAddress } from 'app/shared/model//address.model';
import { IFile } from 'app/shared/model//file.model';

export const enum DeliveryMode {
    HOMEDELEVERY = 'HOMEDELEVERY',
    HOMEWITHDRAWAL = 'HOMEWITHDRAWAL'
}

export interface ITravel {
    id?: number;
    departureDate?: Moment;
    arrivalDate?: Moment;
    departureCity?: string;
    arrivalCity?: string;
    availableKGs?: number;
    pricePerKG?: number;
    travelMode?: string;
    deleveryMode?: DeliveryMode;
    howToContactDescription?: string;
    complementaryRules?: string;
    bookable?: boolean;
    accessCode?: string;
    destinationAddress?: IAddress;
    travelProofs?: IFile[];
}

export class Travel implements ITravel {
    constructor(
        public id?: number,
        public departureDate?: Moment,
        public arrivalDate?: Moment,
        public departureCity?: string,
        public arrivalCity?: string,
        public availableKGs?: number,
        public pricePerKG?: number,
        public travelMode?: string,
        public deleveryMode?: DeliveryMode,
        public howToContactDescription?: string,
        public complementaryRules?: string,
        public bookable?: boolean,
        public accessCode?: string,
        public destinationAddress?: IAddress,
        public travelProofs?: IFile[]
    ) {
        this.bookable = this.bookable || false;
    }
}
