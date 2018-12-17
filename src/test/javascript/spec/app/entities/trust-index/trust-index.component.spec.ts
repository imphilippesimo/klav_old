/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { KlavTestModule } from '../../../test.module';
import { TrustIndexComponent } from 'app/entities/trust-index/trust-index.component';
import { TrustIndexService } from 'app/entities/trust-index/trust-index.service';
import { TrustIndex } from 'app/shared/model/trust-index.model';

describe('Component Tests', () => {
    describe('TrustIndex Management Component', () => {
        let comp: TrustIndexComponent;
        let fixture: ComponentFixture<TrustIndexComponent>;
        let service: TrustIndexService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [KlavTestModule],
                declarations: [TrustIndexComponent],
                providers: []
            })
                .overrideTemplate(TrustIndexComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TrustIndexComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TrustIndexService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new TrustIndex(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.trustIndices[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
