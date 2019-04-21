/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { KlavTestModule } from '../../../test.module';
import { PackageTypeDeleteDialogComponent } from 'app/entities/package-type/package-type-delete-dialog.component';
import { PackageTypeService } from 'app/entities/package-type/package-type.service';

describe('Component Tests', () => {
    describe('PackageType Management Delete Component', () => {
        let comp: PackageTypeDeleteDialogComponent;
        let fixture: ComponentFixture<PackageTypeDeleteDialogComponent>;
        let service: PackageTypeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [KlavTestModule],
                declarations: [PackageTypeDeleteDialogComponent]
            })
                .overrideTemplate(PackageTypeDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PackageTypeDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PackageTypeService);
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
