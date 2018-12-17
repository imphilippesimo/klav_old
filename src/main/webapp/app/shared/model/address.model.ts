export interface IAddress {
    id?: number;
    country?: string;
    city?: string;
    stateProvince?: string;
    postalCode?: string;
    streetNumber?: string;
    streetName?: string;
    description?: string;
}

export class Address implements IAddress {
    constructor(
        public id?: number,
        public country?: string,
        public city?: string,
        public stateProvince?: string,
        public postalCode?: string,
        public streetNumber?: string,
        public streetName?: string,
        public description?: string
    ) {}
}
