/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { KlavTestModule } from '../../../test.module';
import { TravelPackageDetailComponent } from 'app/entities/travel-package/travel-package-detail.component';
import { TravelPackage } from 'app/shared/model/travel-package.model';

describe('Component Tests', () => {
    describe('TravelPackage Management Detail Component', () => {
        let comp: TravelPackageDetailComponent;
        let fixture: ComponentFixture<TravelPackageDetailComponent>;
        const route = ({ data: of({ travelPackage: new TravelPackage(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [KlavTestModule],
                declarations: [TravelPackageDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(TravelPackageDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TravelPackageDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.travelPackage).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
