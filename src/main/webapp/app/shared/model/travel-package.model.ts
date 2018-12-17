import { IKlavUser } from 'app/shared/model//klav-user.model';
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
    klavUser?: IKlavUser;
    receiver?: IKlavUser;
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
        public klavUser?: IKlavUser,
        public receiver?: IKlavUser,
        public pictures?: IFile[],
        public booking?: IBooking
    ) {
        this.fragile = this.fragile || false;
    }
}
