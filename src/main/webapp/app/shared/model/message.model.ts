import { Moment } from 'moment';
import { IKlavUser } from 'app/shared/model//klav-user.model';
import { IChat } from 'app/shared/model//chat.model';

export interface IMessage {
    id?: number;
    content?: string;
    sentDate?: Moment;
    sender?: IKlavUser;
    chat?: IChat;
}

export class Message implements IMessage {
    constructor(public id?: number, public content?: string, public sentDate?: Moment, public sender?: IKlavUser, public chat?: IChat) {}
}
