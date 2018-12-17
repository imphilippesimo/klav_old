/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { KlavTestModule } from '../../../test.module';
import { KlavUserUpdateComponent } from 'app/entities/klav-user/klav-user-update.component';
import { KlavUserService } from 'app/entities/klav-user/klav-user.service';
import { KlavUser } from 'app/shared/model/klav-user.model';

describe('Component Tests', () => {
    describe('KlavUser Management Update Component', () => {
        let comp: KlavUserUpdateComponent;
        let fixture: ComponentFixture<KlavUserUpdateComponent>;
        let service: KlavUserService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [KlavTestModule],
                declarations: [KlavUserUpdateComponent]
            })
                .overrideTemplate(KlavUserUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(KlavUserUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(KlavUserService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new KlavUser(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.klavUser = entity;
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
                    const entity = new KlavUser();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.klavUser = entity;
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
