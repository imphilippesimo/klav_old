/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { KlavTestModule } from '../../../test.module';
import { PackageTypeUpdateComponent } from 'app/entities/package-type/package-type-update.component';
import { PackageTypeService } from 'app/entities/package-type/package-type.service';
import { PackageType } from 'app/shared/model/package-type.model';

describe('Component Tests', () => {
    describe('PackageType Management Update Component', () => {
        let comp: PackageTypeUpdateComponent;
        let fixture: ComponentFixture<PackageTypeUpdateComponent>;
        let service: PackageTypeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [KlavTestModule],
                declarations: [PackageTypeUpdateComponent]
            })
                .overrideTemplate(PackageTypeUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PackageTypeUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PackageTypeService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new PackageType(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.packageType = entity;
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
                    const entity = new PackageType();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.packageType = entity;
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
