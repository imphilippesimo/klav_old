import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IKlavUser } from 'app/shared/model/klav-user.model';
import { KlavUserService } from './klav-user.service';
import { IAddress } from 'app/shared/model/address.model';
import { AddressService } from 'app/entities/address';
import { IUser, UserService } from 'app/core';
import { IChat } from 'app/shared/model/chat.model';
import { ChatService } from 'app/entities/chat';

@Component({
    selector: 'jhi-klav-user-update',
    templateUrl: './klav-user-update.component.html'
})
export class KlavUserUpdateComponent implements OnInit {
    klavUser: IKlavUser;
    isSaving: boolean;

    livesats: IAddress[];

    users: IUser[];

    chats: IChat[];
    birthdate: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private klavUserService: KlavUserService,
        private addressService: AddressService,
        private userService: UserService,
        private chatService: ChatService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ klavUser }) => {
            this.klavUser = klavUser;
            this.birthdate = this.klavUser.birthdate != null ? this.klavUser.birthdate.format(DATE_TIME_FORMAT) : null;
        });
        this.addressService.query({ filter: 'klavuser-is-null' }).subscribe(
            (res: HttpResponse<IAddress[]>) => {
                if (!this.klavUser.livesAt || !this.klavUser.livesAt.id) {
                    this.livesats = res.body;
                } else {
                    this.addressService.find(this.klavUser.livesAt.id).subscribe(
                        (subRes: HttpResponse<IAddress>) => {
                            this.livesats = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.userService.query().subscribe(
            (res: HttpResponse<IUser[]>) => {
                this.users = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.chatService.query().subscribe(
            (res: HttpResponse<IChat[]>) => {
                this.chats = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.klavUser.birthdate = this.birthdate != null ? moment(this.birthdate, DATE_TIME_FORMAT) : null;
        if (this.klavUser.id !== undefined) {
            this.subscribeToSaveResponse(this.klavUserService.update(this.klavUser));
        } else {
            this.subscribeToSaveResponse(this.klavUserService.create(this.klavUser));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IKlavUser>>) {
        result.subscribe((res: HttpResponse<IKlavUser>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackAddressById(index: number, item: IAddress) {
        return item.id;
    }

    trackUserById(index: number, item: IUser) {
        return item.id;
    }

    trackChatById(index: number, item: IChat) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}
