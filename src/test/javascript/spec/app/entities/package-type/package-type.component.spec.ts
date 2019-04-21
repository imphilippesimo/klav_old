/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { KlavTestModule } from '../../../test.module';
import { PackageTypeComponent } from 'app/entities/package-type/package-type.component';
import { PackageTypeService } from 'app/entities/package-type/package-type.service';
import { PackageType } from 'app/shared/model/package-type.model';

describe('Component Tests', () => {
    describe('PackageType Management Component', () => {
        let comp: PackageTypeComponent;
        let fixture: ComponentFixture<PackageTypeComponent>;
        let service: PackageTypeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [KlavTestModule],
                declarations: [PackageTypeComponent],
                providers: []
            })
                .overrideTemplate(PackageTypeComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PackageTypeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PackageTypeService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new PackageType(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.packageTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
