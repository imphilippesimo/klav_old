import { Moment } from 'moment';
import { IKlavUser } from 'app/shared/model//klav-user.model';

export interface IReview {
    id?: number;
    note?: number;
    date?: Moment;
    comment?: string;
    klavUser?: IKlavUser;
}

export class Review implements IReview {
    constructor(public id?: number, public note?: number, public date?: Moment, public comment?: string, public klavUser?: IKlavUser) {}
}
