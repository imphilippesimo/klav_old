import { Moment } from 'moment';
import { IAddress } from 'app/shared/model//address.model';
import { IFile } from 'app/shared/model//file.model';
import { IBooking } from 'app/shared/model//booking.model';
import { IReview } from 'app/shared/model//review.model';
import { IChat } from 'app/shared/model//chat.model';

export interface IKlavUser {
    id?: number;
    phoneNumber?: string;
    birthdate?: Moment;
    selfDescription?: string;
    gender?: string;
    nationality?: string;
    login?: string;
    firstName?: string;
    lastName?: string;
    email?: string;
    activated?: boolean;
    activationKey?: string;
    resetKey?: string;
    resetDate?: Moment;
    password?: string;
    livesAt?: IAddress;
    profilePictures?: IFile[];
    bookings?: IBooking[];
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
        public login?: string,
        public firstName?: string,
        public lastName?: string,
        public email?: string,
        public activated?: boolean,
        public activationKey?: string,
        public resetKey?: string,
        public resetDate?: Moment,
        public password?: string,
        public livesAt?: IAddress,
        public profilePictures?: IFile[],
        public bookings?: IBooking[],
        public reviews?: IReview[],
        public chats?: IChat[]
    ) {
        this.activated = this.activated || false;
    }
}
