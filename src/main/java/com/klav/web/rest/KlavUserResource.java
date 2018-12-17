package com.klav.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.klav.domain.KlavUser;
import com.klav.repository.KlavUserRepository;
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
 * REST controller for managing KlavUser.
 */
@RestController
@RequestMapping("/api")
public class KlavUserResource {

    private final Logger log = LoggerFactory.getLogger(KlavUserResource.class);

    private static final String ENTITY_NAME = "klavUser";

    private KlavUserRepository klavUserRepository;

    public KlavUserResource(KlavUserRepository klavUserRepository) {
        this.klavUserRepository = klavUserRepository;
    }

    /**
     * POST  /klav-users : Create a new klavUser.
     *
     * @param klavUser the klavUser to create
     * @return the ResponseEntity with status 201 (Created) and with body the new klavUser, or with status 400 (Bad Request) if the klavUser has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/klav-users")
    @Timed
    public ResponseEntity<KlavUser> createKlavUser(@RequestBody KlavUser klavUser) throws URISyntaxException {
        log.debug("REST request to save KlavUser : {}", klavUser);
        if (klavUser.getId() != null) {
            throw new BadRequestAlertException("A new klavUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        KlavUser result = klavUserRepository.save(klavUser);
        return ResponseEntity.created(new URI("/api/klav-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /klav-users : Updates an existing klavUser.
     *
     * @param klavUser the klavUser to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated klavUser,
     * or with status 400 (Bad Request) if the klavUser is not valid,
     * or with status 500 (Internal Server Error) if the klavUser couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/klav-users")
    @Timed
    public ResponseEntity<KlavUser> updateKlavUser(@RequestBody KlavUser klavUser) throws URISyntaxException {
        log.debug("REST request to update KlavUser : {}", klavUser);
        if (klavUser.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        KlavUser result = klavUserRepository.save(klavUser);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, klavUser.getId().toString()))
            .body(result);
    }

    /**
     * GET  /klav-users : get all the klavUsers.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of klavUsers in body
     */
    @GetMapping("/klav-users")
    @Timed
    public List<KlavUser> getAllKlavUsers(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all KlavUsers");
        return klavUserRepository.findAllWithEagerRelationships();
    }

    /**
     * GET  /klav-users/:id : get the "id" klavUser.
     *
     * @param id the id of the klavUser to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the klavUser, or with status 404 (Not Found)
     */
    @GetMapping("/klav-users/{id}")
    @Timed
    public ResponseEntity<KlavUser> getKlavUser(@PathVariable Long id) {
        log.debug("REST request to get KlavUser : {}", id);
        Optional<KlavUser> klavUser = klavUserRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(klavUser);
    }

    /**
     * DELETE  /klav-users/:id : delete the "id" klavUser.
     *
     * @param id the id of the klavUser to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/klav-users/{id}")
    @Timed
    public ResponseEntity<Void> deleteKlavUser(@PathVariable Long id) {
        log.debug("REST request to delete KlavUser : {}", id);

        klavUserRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
