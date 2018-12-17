/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { KlavTestModule } from '../../../test.module';
import { KlavUserComponent } from 'app/entities/klav-user/klav-user.component';
import { KlavUserService } from 'app/entities/klav-user/klav-user.service';
import { KlavUser } from 'app/shared/model/klav-user.model';

describe('Component Tests', () => {
    describe('KlavUser Management Component', () => {
        let comp: KlavUserComponent;
        let fixture: ComponentFixture<KlavUserComponent>;
        let service: KlavUserService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [KlavTestModule],
                declarations: [KlavUserComponent],
                providers: []
            })
                .overrideTemplate(KlavUserComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(KlavUserComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(KlavUserService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new KlavUser(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.klavUsers[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
