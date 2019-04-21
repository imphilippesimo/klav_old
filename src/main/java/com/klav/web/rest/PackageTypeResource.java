package com.klav.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.klav.domain.PackageType;
import com.klav.repository.PackageTypeRepository;
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
 * REST controller for managing PackageType.
 */
@RestController
@RequestMapping("/api")
public class PackageTypeResource {

    private final Logger log = LoggerFactory.getLogger(PackageTypeResource.class);

    private static final String ENTITY_NAME = "packageType";

    private PackageTypeRepository packageTypeRepository;

    public PackageTypeResource(PackageTypeRepository packageTypeRepository) {
        this.packageTypeRepository = packageTypeRepository;
    }

    /**
     * POST  /package-types : Create a new packageType.
     *
     * @param packageType the packageType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new packageType, or with status 400 (Bad Request) if the packageType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/package-types")
    @Timed
    public ResponseEntity<PackageType> createPackageType(@RequestBody PackageType packageType) throws URISyntaxException {
        log.debug("REST request to save PackageType : {}", packageType);
        if (packageType.getId() != null) {
            throw new BadRequestAlertException("A new packageType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PackageType result = packageTypeRepository.save(packageType);
        return ResponseEntity.created(new URI("/api/package-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /package-types : Updates an existing packageType.
     *
     * @param packageType the packageType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated packageType,
     * or with status 400 (Bad Request) if the packageType is not valid,
     * or with status 500 (Internal Server Error) if the packageType couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/package-types")
    @Timed
    public ResponseEntity<PackageType> updatePackageType(@RequestBody PackageType packageType) throws URISyntaxException {
        log.debug("REST request to update PackageType : {}", packageType);
        if (packageType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PackageType result = packageTypeRepository.save(packageType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, packageType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /package-types : get all the packageTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of packageTypes in body
     */
    @GetMapping("/package-types")
    @Timed
    public List<PackageType> getAllPackageTypes() {
        log.debug("REST request to get all PackageTypes");
        return packageTypeRepository.findAll();
    }

    /**
     * GET  /package-types/:id : get the "id" packageType.
     *
     * @param id the id of the packageType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the packageType, or with status 404 (Not Found)
     */
    @GetMapping("/package-types/{id}")
    @Timed
    public ResponseEntity<PackageType> getPackageType(@PathVariable Long id) {
        log.debug("REST request to get PackageType : {}", id);
        Optional<PackageType> packageType = packageTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(packageType);
    }

    /**
     * DELETE  /package-types/:id : delete the "id" packageType.
     *
     * @param id the id of the packageType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/package-types/{id}")
    @Timed
    public ResponseEntity<Void> deletePackageType(@PathVariable Long id) {
        log.debug("REST request to delete PackageType : {}", id);

        packageTypeRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
