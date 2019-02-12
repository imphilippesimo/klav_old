package com.klav.web.rest.ext;

import com.codahale.metrics.annotation.Timed;
import com.klav.service.dto.TravelDTO;
import com.klav.service.ext.TravelServiceExtended;
import com.klav.web.rest.TravelResource;
import com.klav.web.rest.errors.BadRequestAlertException;
import com.klav.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;


/**
 * REST controller for managing Travel.
 */
@RestController
@RequestMapping("/api/extended")
public class TravelResourceExtended {


    private final Logger log = LoggerFactory.getLogger(TravelResourceExtended.class);

    private static final String ENTITY_NAME = "travel";

    private TravelServiceExtended travelService;

    public TravelResourceExtended(TravelServiceExtended travelService) {
        this.travelService = travelService;
    }

    /**
     * POST  /travels : Create a new travel.
     *
     * @param travelDTO the travel to create
     * @return the ResponseEntity with status 201 (Created) and with body the new travel, or with status 400 (Bad Request) if the travel has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/travels")
    @Timed
    public ResponseEntity<TravelDTO> createTravel(@Valid @RequestBody TravelDTO travelDTO) throws URISyntaxException {
        log.debug("REST request to save Travel : {}", travelDTO);
        if (travelDTO.getId() != null) {
            throw new BadRequestAlertException("A new travel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if (!travelDTO.getArrivalDate().isBefore(travelDTO.getDepartureDate())) {
            throw new BadRequestAlertException("The arrival date cannot be before the departure date", ENTITY_NAME, "noncoherentdates");


        }

        if (!travelDTO.getDepartureDate().isBefore(Instant.now()))
            throw new BadRequestAlertException("The departure date cannot be before today", ENTITY_NAME, "noncoherentdates");

        TravelDTO result = travelService.createOrUpdateTravel(travelDTO);
        return ResponseEntity.created(new URI("/api/extended/travels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /travels : Updates an existing travel.
     *
     * @param travelDTO the travel to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated travel,
     * or with status 400 (Bad Request) if the travel is not valid,
     * or with status 500 (Internal Server Error) if the travel couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/travels")
    @Timed
    public ResponseEntity<TravelDTO> updateTravel(@Valid @RequestBody TravelDTO travelDTO) throws
        URISyntaxException {
        log.debug("REST request to update Travel : {}", travelDTO);
        if (travelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TravelDTO result = travelService.createOrUpdateTravel(travelDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, travelDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /travels : get all the travels.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of travels in body
     */
    @GetMapping("/travels")
    @Timed
    public List<TravelDTO> getAllTravels() {
        log.debug("REST request to get all Travels");
        return travelService.getAllTravels();
    }

    /**
     * GET  /travels/:id : get the "id" travel.
     *
     * @param id the id of the travel to retrieve
     * @return the ResponseEntity with status 200 (OK) and  with body the travel, or with status 404 (Not Found)
     */
    @GetMapping("/travels/{id}")
    @Timed
    public ResponseEntity<TravelDTO> getTravel(@PathVariable Long id) {
        log.debug("REST request to get Travel : {}", id);
        Optional<TravelDTO> travel = travelService.getTravel(id);
        return ResponseUtil.wrapOrNotFound(travel);
    }

    /**
     * DELETE  /travels/:id : delete the "id" travel.
     *
     * @param id the id of the travel to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/travels/{id}")
    @Timed
    public ResponseEntity<Void> deleteTravel(@PathVariable Long id) {
        log.debug("REST request to delete Travel : {}", id);

        travelService.deleteTravel(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
