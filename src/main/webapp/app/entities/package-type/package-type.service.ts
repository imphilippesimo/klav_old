import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPackageType } from 'app/shared/model/package-type.model';

type EntityResponseType = HttpResponse<IPackageType>;
type EntityArrayResponseType = HttpResponse<IPackageType[]>;

@Injectable({ providedIn: 'root' })
export class PackageTypeService {
    public resourceUrl = SERVER_API_URL + 'api/package-types';

    constructor(private http: HttpClient) {}

    create(packageType: IPackageType): Observable<EntityResponseType> {
        return this.http.post<IPackageType>(this.resourceUrl, packageType, { observe: 'response' });
    }

    update(packageType: IPackageType): Observable<EntityResponseType> {
        return this.http.put<IPackageType>(this.resourceUrl, packageType, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IPackageType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IPackageType[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
