import { Moment } from 'moment';
import { IAddress } from 'app/shared/model//address.model';
import { IFile } from 'app/shared/model//file.model';
import { IPackageType } from 'app/shared/model//package-type.model';

export const enum DeliveryMode {
    HOMEDELEVERY = 'HOMEDELEVERY',
    HOMEWITHDRAWAL = 'HOMEWITHDRAWAL'
}

export interface ITravel {
    id?: number;
    departureDate?: Moment;
    arrivalDate?: Moment;
    departureCountry?: string;
    departureCity?: string;
    arrivalCountry?: string;
    arrivalCity?: string;
    availableKGs?: number;
    pricePerKG?: number;
    travelMode?: string;
    isFreeOfCharge?: boolean;
    isAcceptingFragilePackages?: boolean;
    deleveryMode?: DeliveryMode;
    howToContactDescription?: string;
    complementaryRules?: string;
    bookable?: boolean;
    accessCode?: string;
    destinationAddress?: IAddress;
    travelProofs?: IFile[];
    acceptedPackageTypes?: IPackageType[];
}

export class Travel implements ITravel {
    constructor(
        public id?: number,
        public departureDate?: Moment,
        public arrivalDate?: Moment,
        public departureCountry?: string,
        public departureCity?: string,
        public arrivalCountry?: string,
        public arrivalCity?: string,
        public availableKGs?: number,
        public pricePerKG?: number,
        public travelMode?: string,
        public isFreeOfCharge?: boolean,
        public isAcceptingFragilePackages?: boolean,
        public deleveryMode?: DeliveryMode,
        public howToContactDescription?: string,
        public complementaryRules?: string,
        public bookable?: boolean,
        public accessCode?: string,
        public destinationAddress?: IAddress,
        public travelProofs?: IFile[],
        public acceptedPackageTypes?: IPackageType[]
    ) {
        this.isFreeOfCharge = this.isFreeOfCharge || false;
        this.isAcceptingFragilePackages = this.isAcceptingFragilePackages || false;
        this.bookable = this.bookable || false;
    }
}
