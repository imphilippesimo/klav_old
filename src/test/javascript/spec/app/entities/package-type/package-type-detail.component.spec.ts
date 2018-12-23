/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { KlavTestModule } from '../../../test.module';
import { PackageTypeDetailComponent } from 'app/entities/package-type/package-type-detail.component';
import { PackageType } from 'app/shared/model/package-type.model';

describe('Component Tests', () => {
    describe('PackageType Management Detail Component', () => {
        let comp: PackageTypeDetailComponent;
        let fixture: ComponentFixture<PackageTypeDetailComponent>;
        const route = ({ data: of({ packageType: new PackageType(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [KlavTestModule],
                declarations: [PackageTypeDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(PackageTypeDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PackageTypeDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.packageType).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
