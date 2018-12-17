package com.klav.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.klav.domain.TravelPackage;
import com.klav.repository.TravelPackageRepository;
import com.klav.web.rest.errors.BadRequestAlertException;
import com.klav.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing TravelPackage.
 */
@RestController
@RequestMapping("/api")
public class TravelPackageResource {

    private final Logger log = LoggerFactory.getLogger(TravelPackageResource.class);

    private static final String ENTITY_NAME = "travelPackage";

    private TravelPackageRepository travelPackageRepository;

    public TravelPackageResource(TravelPackageRepository travelPackageRepository) {
        this.travelPackageRepository = travelPackageRepository;
    }

    /**
     * POST  /travel-packages : Create a new travelPackage.
     *
     * @param travelPackage the travelPackage to create
     * @return the ResponseEntity with status 201 (Created) and with body the new travelPackage, or with status 400 (Bad Request) if the travelPackage has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/travel-packages")
    @Timed
    public ResponseEntity<TravelPackage> createTravelPackage(@RequestBody TravelPackage travelPackage) throws URISyntaxException {
        log.debug("REST request to save TravelPackage : {}", travelPackage);
        if (travelPackage.getId() != null) {
            throw new BadRequestAlertException("A new travelPackage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TravelPackage result = travelPackageRepository.save(travelPackage);
        return ResponseEntity.created(new URI("/api/travel-packages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /travel-packages : Updates an existing travelPackage.
     *
     * @param travelPackage the travelPackage to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated travelPackage,
     * or with status 400 (Bad Request) if the travelPackage is not valid,
     * or with status 500 (Internal Server Error) if the travelPackage couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/travel-packages")
    @Timed
    public ResponseEntity<TravelPackage> updateTravelPackage(@RequestBody TravelPackage travelPackage) throws URISyntaxException {
        log.debug("REST request to update TravelPackage : {}", travelPackage);
        if (travelPackage.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TravelPackage result = travelPackageRepository.save(travelPackage);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, travelPackage.getId().toString()))
            .body(result);
    }

    /**
     * GET  /travel-packages : get all the travelPackages.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of travelPackages in body
     */
    @GetMapping("/travel-packages")
    @Timed
    public List<TravelPackage> getAllTravelPackages() {
        log.debug("REST request to get all TravelPackages");
        return travelPackageRepository.findAll();
    }

    /**
     * GET  /travel-packages/:id : get the "id" travelPackage.
     *
     * @param id the id of the travelPackage to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the travelPackage, or with status 404 (Not Found)
     */
    @GetMapping("/travel-packages/{id}")
    @Timed
    public ResponseEntity<TravelPackage> getTravelPackage(@PathVariable Long id) {
        log.debug("REST request to get TravelPackage : {}", id);
        Optional<TravelPackage> travelPackage = travelPackageRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(travelPackage);
    }

    /**
     * DELETE  /travel-packages/:id : delete the "id" travelPackage.
     *
     * @param id the id of the travelPackage to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/travel-packages/{id}")
    @Timed
    public ResponseEntity<Void> deleteTravelPackage(@PathVariable Long id) {
        log.debug("REST request to delete TravelPackage : {}", id);

        travelPackageRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
