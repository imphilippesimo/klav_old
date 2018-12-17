import { Moment } from 'moment';
import { IBooking } from 'app/shared/model//booking.model';

export interface ITrustIndex {
    id?: number;
    value?: number;
    date?: Moment;
    booking?: IBooking;
}

export class TrustIndex implements ITrustIndex {
    constructor(public id?: number, public value?: number, public date?: Moment, public booking?: IBooking) {}
}
