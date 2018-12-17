import { Moment } from 'moment';
import { ITravel } from 'app/shared/model//travel.model';
import { IKlavUser } from 'app/shared/model//klav-user.model';
import { ITravelPackage } from 'app/shared/model//travel-package.model';

export const enum FileType {
    IDPROOF = 'IDPROOF',
    TRAVELPROOF = 'TRAVELPROOF',
    PACKAGEPICTURE = 'PACKAGEPICTURE',
    PROFILEPICTURE = 'PROFILEPICTURE'
}

export interface IFile {
    id?: number;
    fileURL?: string;
    name?: string;
    updatedDate?: Moment;
    mimeType?: string;
    type?: FileType;
    travel?: ITravel;
    klavUser?: IKlavUser;
    travelPackage?: ITravelPackage;
}

export class File implements IFile {
    constructor(
        public id?: number,
        public fileURL?: string,
        public name?: string,
        public updatedDate?: Moment,
        public mimeType?: string,
        public type?: FileType,
        public travel?: ITravel,
        public klavUser?: IKlavUser,
        public travelPackage?: ITravelPackage
    ) {}
}
