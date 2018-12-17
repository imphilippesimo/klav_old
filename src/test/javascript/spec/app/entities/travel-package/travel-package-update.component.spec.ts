/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { KlavTestModule } from '../../../test.module';
import { TravelPackageUpdateComponent } from 'app/entities/travel-package/travel-package-update.component';
import { TravelPackageService } from 'app/entities/travel-package/travel-package.service';
import { TravelPackage } from 'app/shared/model/travel-package.model';

describe('Component Tests', () => {
    describe('TravelPackage Management Update Component', () => {
        let comp: TravelPackageUpdateComponent;
        let fixture: ComponentFixture<TravelPackageUpdateComponent>;
        let service: TravelPackageService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [KlavTestModule],
                declarations: [TravelPackageUpdateComponent]
            })
                .overrideTemplate(TravelPackageUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TravelPackageUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TravelPackageService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new TravelPackage(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.travelPackage = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new TravelPackage();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.travelPackage = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
