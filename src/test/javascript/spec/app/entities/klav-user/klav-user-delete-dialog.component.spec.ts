/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { KlavTestModule } from '../../../test.module';
import { KlavUserDeleteDialogComponent } from 'app/entities/klav-user/klav-user-delete-dialog.component';
import { KlavUserService } from 'app/entities/klav-user/klav-user.service';

describe('Component Tests', () => {
    describe('KlavUser Management Delete Component', () => {
        let comp: KlavUserDeleteDialogComponent;
        let fixture: ComponentFixture<KlavUserDeleteDialogComponent>;
        let service: KlavUserService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [KlavTestModule],
                declarations: [KlavUserDeleteDialogComponent]
            })
                .overrideTemplate(KlavUserDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(KlavUserDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(KlavUserService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it(
                'Should call delete service on confirmDelete',
                inject(
                    [],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });
});
