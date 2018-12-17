package com.klav.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.klav.domain.TrustIndex;
import com.klav.repository.TrustIndexRepository;
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
 * REST controller for managing TrustIndex.
 */
@RestController
@RequestMapping("/api")
public class TrustIndexResource {

    private final Logger log = LoggerFactory.getLogger(TrustIndexResource.class);

    private static final String ENTITY_NAME = "trustIndex";

    private TrustIndexRepository trustIndexRepository;

    public TrustIndexResource(TrustIndexRepository trustIndexRepository) {
        this.trustIndexRepository = trustIndexRepository;
    }

    /**
     * POST  /trust-indices : Create a new trustIndex.
     *
     * @param trustIndex the trustIndex to create
     * @return the ResponseEntity with status 201 (Created) and with body the new trustIndex, or with status 400 (Bad Request) if the trustIndex has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/trust-indices")
    @Timed
    public ResponseEntity<TrustIndex> createTrustIndex(@RequestBody TrustIndex trustIndex) throws URISyntaxException {
        log.debug("REST request to save TrustIndex : {}", trustIndex);
        if (trustIndex.getId() != null) {
            throw new BadRequestAlertException("A new trustIndex cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TrustIndex result = trustIndexRepository.save(trustIndex);
        return ResponseEntity.created(new URI("/api/trust-indices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /trust-indices : Updates an existing trustIndex.
     *
     * @param trustIndex the trustIndex to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated trustIndex,
     * or with status 400 (Bad Request) if the trustIndex is not valid,
     * or with status 500 (Internal Server Error) if the trustIndex couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/trust-indices")
    @Timed
    public ResponseEntity<TrustIndex> updateTrustIndex(@RequestBody TrustIndex trustIndex) throws URISyntaxException {
        log.debug("REST request to update TrustIndex : {}", trustIndex);
        if (trustIndex.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TrustIndex result = trustIndexRepository.save(trustIndex);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, trustIndex.getId().toString()))
            .body(result);
    }

    /**
     * GET  /trust-indices : get all the trustIndices.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of trustIndices in body
     */
    @GetMapping("/trust-indices")
    @Timed
    public List<TrustIndex> getAllTrustIndices() {
        log.debug("REST request to get all TrustIndices");
        return trustIndexRepository.findAll();
    }

    /**
     * GET  /trust-indices/:id : get the "id" trustIndex.
     *
     * @param id the id of the trustIndex to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the trustIndex, or with status 404 (Not Found)
     */
    @GetMapping("/trust-indices/{id}")
    @Timed
    public ResponseEntity<TrustIndex> getTrustIndex(@PathVariable Long id) {
        log.debug("REST request to get TrustIndex : {}", id);
        Optional<TrustIndex> trustIndex = trustIndexRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(trustIndex);
    }

    /**
     * DELETE  /trust-indices/:id : delete the "id" trustIndex.
     *
     * @param id the id of the trustIndex to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/trust-indices/{id}")
    @Timed
    public ResponseEntity<Void> deleteTrustIndex(@PathVariable Long id) {
        log.debug("REST request to delete TrustIndex : {}", id);

        trustIndexRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
