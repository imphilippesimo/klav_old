/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { KlavTestModule } from '../../../test.module';
import { TrustIndexDetailComponent } from 'app/entities/trust-index/trust-index-detail.component';
import { TrustIndex } from 'app/shared/model/trust-index.model';

describe('Component Tests', () => {
    describe('TrustIndex Management Detail Component', () => {
        let comp: TrustIndexDetailComponent;
        let fixture: ComponentFixture<TrustIndexDetailComponent>;
        const route = ({ data: of({ trustIndex: new TrustIndex(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [KlavTestModule],
                declarations: [TrustIndexDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(TrustIndexDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TrustIndexDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.trustIndex).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
