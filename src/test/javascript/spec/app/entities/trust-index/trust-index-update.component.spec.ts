/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { KlavTestModule } from '../../../test.module';
import { TrustIndexUpdateComponent } from 'app/entities/trust-index/trust-index-update.component';
import { TrustIndexService } from 'app/entities/trust-index/trust-index.service';
import { TrustIndex } from 'app/shared/model/trust-index.model';

describe('Component Tests', () => {
    describe('TrustIndex Management Update Component', () => {
        let comp: TrustIndexUpdateComponent;
        let fixture: ComponentFixture<TrustIndexUpdateComponent>;
        let service: TrustIndexService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [KlavTestModule],
                declarations: [TrustIndexUpdateComponent]
            })
                .overrideTemplate(TrustIndexUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TrustIndexUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TrustIndexService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new TrustIndex(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.trustIndex = entity;
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
                    const entity = new TrustIndex();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.trustIndex = entity;
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
