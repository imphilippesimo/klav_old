/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { KlavTestModule } from '../../../test.module';
import { KlavUserDetailComponent } from 'app/entities/klav-user/klav-user-detail.component';
import { KlavUser } from 'app/shared/model/klav-user.model';

describe('Component Tests', () => {
    describe('KlavUser Management Detail Component', () => {
        let comp: KlavUserDetailComponent;
        let fixture: ComponentFixture<KlavUserDetailComponent>;
        const route = ({ data: of({ klavUser: new KlavUser(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [KlavTestModule],
                declarations: [KlavUserDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(KlavUserDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(KlavUserDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.klavUser).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
