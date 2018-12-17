/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { KlavTestModule } from '../../../test.module';
import { TravelPackageDeleteDialogComponent } from 'app/entities/travel-package/travel-package-delete-dialog.component';
import { TravelPackageService } from 'app/entities/travel-package/travel-package.service';

describe('Component Tests', () => {
    describe('TravelPackage Management Delete Component', () => {
        let comp: TravelPackageDeleteDialogComponent;
        let fixture: ComponentFixture<TravelPackageDeleteDialogComponent>;
        let service: TravelPackageService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [KlavTestModule],
                declarations: [TravelPackageDeleteDialogComponent]
            })
                .overrideTemplate(TravelPackageDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TravelPackageDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TravelPackageService);
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
