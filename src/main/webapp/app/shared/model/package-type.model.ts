import { ITravel } from 'app/shared/model//travel.model';

export interface IPackageType {
    id?: number;
    name?: string;
    travel?: ITravel;
}

export class PackageType implements IPackageType {
    constructor(public id?: number, public name?: string, public travel?: ITravel) {}
}
