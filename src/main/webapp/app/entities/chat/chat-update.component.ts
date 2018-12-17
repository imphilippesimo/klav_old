import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IChat } from 'app/shared/model/chat.model';
import { ChatService } from './chat.service';
import { IKlavUser } from 'app/shared/model/klav-user.model';
import { KlavUserService } from 'app/entities/klav-user';

@Component({
    selector: 'jhi-chat-update',
    templateUrl: './chat-update.component.html'
})
export class ChatUpdateComponent implements OnInit {
    chat: IChat;
    isSaving: boolean;

    klavusers: IKlavUser[];
    creationDate: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private chatService: ChatService,
        private klavUserService: KlavUserService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ chat }) => {
            this.chat = chat;
            this.creationDate = this.chat.creationDate != null ? this.chat.creationDate.format(DATE_TIME_FORMAT) : null;
        });
        this.klavUserService.query().subscribe(
            (res: HttpResponse<IKlavUser[]>) => {
                this.klavusers = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.chat.creationDate = this.creationDate != null ? moment(this.creationDate, DATE_TIME_FORMAT) : null;
        if (this.chat.id !== undefined) {
            this.subscribeToSaveResponse(this.chatService.update(this.chat));
        } else {
            this.subscribeToSaveResponse(this.chatService.create(this.chat));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IChat>>) {
        result.subscribe((res: HttpResponse<IChat>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackKlavUserById(index: number, item: IKlavUser) {
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
