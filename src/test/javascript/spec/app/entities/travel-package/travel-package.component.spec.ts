/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { KlavTestModule } from '../../../test.module';
import { TravelPackageComponent } from 'app/entities/travel-package/travel-package.component';
import { TravelPackageService } from 'app/entities/travel-package/travel-package.service';
import { TravelPackage } from 'app/shared/model/travel-package.model';

describe('Component Tests', () => {
    describe('TravelPackage Management Component', () => {
        let comp: TravelPackageComponent;
        let fixture: ComponentFixture<TravelPackageComponent>;
        let service: TravelPackageService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [KlavTestModule],
                declarations: [TravelPackageComponent],
                providers: []
            })
                .overrideTemplate(TravelPackageComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TravelPackageComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TravelPackageService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new TravelPackage(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.travelPackages[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
