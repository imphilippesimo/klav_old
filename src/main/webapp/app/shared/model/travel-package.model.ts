import { IKlavUser } from 'app/shared/model//klav-user.model';
import { IAddress } from 'app/shared/model//address.model';
import { IPackageType } from 'app/shared/model//package-type.model';
import { IFile } from 'app/shared/model//file.model';
import { IBooking } from 'app/shared/model//booking.model';

export const enum DeliveryMode {
    HOMEDELEVERY = 'HOMEDELEVERY',
    HOMEWITHDRAWAL = 'HOMEWITHDRAWAL'
}

export interface ITravelPackage {
    id?: number;
    title?: string;
    weight?: number;
    accessCode?: string;
    deleveryCode?: string;
    desiredDeleveryMode?: DeliveryMode;
    description?: string;
    pricePerKG?: number;
    fragile?: boolean;
    receiver?: IKlavUser;
    destinationAddress?: IAddress;
    type?: IPackageType;
    pictures?: IFile[];
    booking?: IBooking;
}

export class TravelPackage implements ITravelPackage {
    constructor(
        public id?: number,
        public title?: string,
        public weight?: number,
        public accessCode?: string,
        public deleveryCode?: string,
        public desiredDeleveryMode?: DeliveryMode,
        public description?: string,
        public pricePerKG?: number,
        public fragile?: boolean,
        public receiver?: IKlavUser,
        public destinationAddress?: IAddress,
        public type?: IPackageType,
        public pictures?: IFile[],
        public booking?: IBooking
    ) {
        this.fragile = this.fragile || false;
    }
}
