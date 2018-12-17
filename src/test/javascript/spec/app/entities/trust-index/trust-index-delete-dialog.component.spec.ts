/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { KlavTestModule } from '../../../test.module';
import { TrustIndexDeleteDialogComponent } from 'app/entities/trust-index/trust-index-delete-dialog.component';
import { TrustIndexService } from 'app/entities/trust-index/trust-index.service';

describe('Component Tests', () => {
    describe('TrustIndex Management Delete Component', () => {
        let comp: TrustIndexDeleteDialogComponent;
        let fixture: ComponentFixture<TrustIndexDeleteDialogComponent>;
        let service: TrustIndexService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [KlavTestModule],
                declarations: [TrustIndexDeleteDialogComponent]
            })
                .overrideTemplate(TrustIndexDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TrustIndexDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TrustIndexService);
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
