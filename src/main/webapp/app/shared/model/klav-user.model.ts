import { Moment } from 'moment';
import { IAddress } from 'app/shared/model//address.model';
import { IFile } from 'app/shared/model//file.model';
import { IBooking } from 'app/shared/model//booking.model';
import { ITravelPackage } from 'app/shared/model//travel-package.model';
import { IReview } from 'app/shared/model//review.model';
import { IChat } from 'app/shared/model//chat.model';

export interface IKlavUser {
    id?: number;
    phoneNumber?: string;
    birthdate?: Moment;
    selfDescription?: string;
    gender?: string;
    nationality?: string;
    livesAt?: IAddress;
    profilePictures?: IFile[];
    bookings?: IBooking[];
    travels?: ITravelPackage[];
    reviews?: IReview[];
    chats?: IChat[];
}

export class KlavUser implements IKlavUser {
    constructor(
        public id?: number,
        public phoneNumber?: string,
        public birthdate?: Moment,
        public selfDescription?: string,
        public gender?: string,
        public nationality?: string,
        public livesAt?: IAddress,
        public profilePictures?: IFile[],
        public bookings?: IBooking[],
        public travels?: ITravelPackage[],
        public reviews?: IReview[],
        public chats?: IChat[]
    ) {}
}
