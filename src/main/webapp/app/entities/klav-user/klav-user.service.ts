import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IKlavUser } from 'app/shared/model/klav-user.model';

type EntityResponseType = HttpResponse<IKlavUser>;
type EntityArrayResponseType = HttpResponse<IKlavUser[]>;

@Injectable({ providedIn: 'root' })
export class KlavUserService {
    public resourceUrl = SERVER_API_URL + 'api/klav-users';

    constructor(private http: HttpClient) {}

    create(klavUser: IKlavUser): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(klavUser);
        return this.http
            .post<IKlavUser>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(klavUser: IKlavUser): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(klavUser);
        return this.http
            .put<IKlavUser>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IKlavUser>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IKlavUser[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(klavUser: IKlavUser): IKlavUser {
        const copy: IKlavUser = Object.assign({}, klavUser, {
            birthdate: klavUser.birthdate != null && klavUser.birthdate.isValid() ? klavUser.birthdate.toJSON() : null,
            resetDate: klavUser.resetDate != null && klavUser.resetDate.isValid() ? klavUser.resetDate.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.birthdate = res.body.birthdate != null ? moment(res.body.birthdate) : null;
        res.body.resetDate = res.body.resetDate != null ? moment(res.body.resetDate) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((klavUser: IKlavUser) => {
            klavUser.birthdate = klavUser.birthdate != null ? moment(klavUser.birthdate) : null;
            klavUser.resetDate = klavUser.resetDate != null ? moment(klavUser.resetDate) : null;
        });
        return res;
    }
}
