import { Moment } from 'moment';
import { IMessage } from 'app/shared/model//message.model';
import { IKlavUser } from 'app/shared/model//klav-user.model';

export interface IChat {
    id?: number;
    creationDate?: Moment;
    messages?: IMessage[];
    klavUsers?: IKlavUser[];
}

export class Chat implements IChat {
    constructor(public id?: number, public creationDate?: Moment, public messages?: IMessage[], public klavUsers?: IKlavUser[]) {}
}
