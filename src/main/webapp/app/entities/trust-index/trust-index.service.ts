import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITrustIndex } from 'app/shared/model/trust-index.model';

type EntityResponseType = HttpResponse<ITrustIndex>;
type EntityArrayResponseType = HttpResponse<ITrustIndex[]>;

@Injectable({ providedIn: 'root' })
export class TrustIndexService {
    public resourceUrl = SERVER_API_URL + 'api/trust-indices';

    constructor(private http: HttpClient) {}

    create(trustIndex: ITrustIndex): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(trustIndex);
        return this.http
            .post<ITrustIndex>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(trustIndex: ITrustIndex): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(trustIndex);
        return this.http
            .put<ITrustIndex>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ITrustIndex>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ITrustIndex[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(trustIndex: ITrustIndex): ITrustIndex {
        const copy: ITrustIndex = Object.assign({}, trustIndex, {
            date: trustIndex.date != null && trustIndex.date.isValid() ? trustIndex.date.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.date = res.body.date != null ? moment(res.body.date) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((trustIndex: ITrustIndex) => {
            trustIndex.date = trustIndex.date != null ? moment(trustIndex.date) : null;
        });
        return res;
    }
}
