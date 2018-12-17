import { Moment } from 'moment';
import { IBooking } from 'app/shared/model//booking.model';

export interface IStatus {
    id?: number;
    value?: string;
    date?: Moment;
    reason?: string;
    comment?: string;
    booking?: IBooking;
}

export class Status implements IStatus {
    constructor(
        public id?: number,
        public value?: string,
        public date?: Moment,
        public reason?: string,
        public comment?: string,
        public booking?: IBooking
    ) {}
}
