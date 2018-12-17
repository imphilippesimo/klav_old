package com.klav.web.rest;

import com.klav.KlavApp;

import com.klav.domain.Travel;
import com.klav.repository.TravelRepository;
import com.klav.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;


import static com.klav.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.klav.domain.enumeration.DeliveryMode;
/**
 * Test class for the TravelResource REST controller.
 *
 * @see TravelResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = KlavApp.class)
public class TravelResourceIntTest {

    private static final Instant DEFAULT_DEPARTURE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DEPARTURE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_ARRIVAL_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ARRIVAL_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_DEPARTURE_CITY = "AAAAAAAAAA";
    private static final String UPDATED_DEPARTURE_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_ARRIVAL_CITY = "AAAAAAAAAA";
    private static final String UPDATED_ARRIVAL_CITY = "BBBBBBBBBB";

    private static final Double DEFAULT_AVAILABLE_K_GS = 1D;
    private static final Double UPDATED_AVAILABLE_K_GS = 2D;

    private static final Float DEFAULT_PRICE_PER_KG = 1F;
    private static final Float UPDATED_PRICE_PER_KG = 2F;

    private static final String DEFAULT_TRAVEL_MODE = "AAAAAAAAAA";
    private static final String UPDATED_TRAVEL_MODE = "BBBBBBBBBB";

    private static final DeliveryMode DEFAULT_DELEVERY_MODE = DeliveryMode.HOMEDELEVERY;
    private static final DeliveryMode UPDATED_DELEVERY_MODE = DeliveryMode.HOMEWITHDRAWAL;

    private static final String DEFAULT_HOW_TO_CONTACT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_HOW_TO_CONTACT_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_COMPLEMENTARY_RULES = "AAAAAAAAAA";
    private static final String UPDATED_COMPLEMENTARY_RULES = "BBBBBBBBBB";

    private static final Boolean DEFAULT_BOOKABLE = false;
    private static final Boolean UPDATED_BOOKABLE = true;

    private static final String DEFAULT_ACCESS_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ACCESS_CODE = "BBBBBBBBBB";

    @Autowired
    private TravelRepository travelRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTravelMockMvc;

    private Travel travel;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TravelResource travelResource = new TravelResource(travelRepository);
        this.restTravelMockMvc = MockMvcBuilders.standaloneSetup(travelResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Travel createEntity(EntityManager em) {
        Travel travel = new Travel()
            .departureDate(DEFAULT_DEPARTURE_DATE)
            .arrivalDate(DEFAULT_ARRIVAL_DATE)
            .departureCity(DEFAULT_DEPARTURE_CITY)
            .arrivalCity(DEFAULT_ARRIVAL_CITY)
            .availableKGs(DEFAULT_AVAILABLE_K_GS)
            .pricePerKG(DEFAULT_PRICE_PER_KG)
            .travelMode(DEFAULT_TRAVEL_MODE)
            .deleveryMode(DEFAULT_DELEVERY_MODE)
            .howToContactDescription(DEFAULT_HOW_TO_CONTACT_DESCRIPTION)
            .complementaryRules(DEFAULT_COMPLEMENTARY_RULES)
            .bookable(DEFAULT_BOOKABLE)
            .accessCode(DEFAULT_ACCESS_CODE);
        return travel;
    }

    @Before
    public void initTest() {
        travel = createEntity(em);
    }

    @Test
    @Transactional
    public void createTravel() throws Exception {
        int databaseSizeBeforeCreate = travelRepository.findAll().size();

        // Create the Travel
        restTravelMockMvc.perform(post("/api/travels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(travel)))
            .andExpect(status().isCreated());

        // Validate the Travel in the database
        List<Travel> travelList = travelRepository.findAll();
        assertThat(travelList).hasSize(databaseSizeBeforeCreate + 1);
        Travel testTravel = travelList.get(travelList.size() - 1);
        assertThat(testTravel.getDepartureDate()).isEqualTo(DEFAULT_DEPARTURE_DATE);
        assertThat(testTravel.getArrivalDate()).isEqualTo(DEFAULT_ARRIVAL_DATE);
        assertThat(testTravel.getDepartureCity()).isEqualTo(DEFAULT_DEPARTURE_CITY);
        assertThat(testTravel.getArrivalCity()).isEqualTo(DEFAULT_ARRIVAL_CITY);
        assertThat(testTravel.getAvailableKGs()).isEqualTo(DEFAULT_AVAILABLE_K_GS);
        assertThat(testTravel.getPricePerKG()).isEqualTo(DEFAULT_PRICE_PER_KG);
        assertThat(testTravel.getTravelMode()).isEqualTo(DEFAULT_TRAVEL_MODE);
        assertThat(testTravel.getDeleveryMode()).isEqualTo(DEFAULT_DELEVERY_MODE);
        assertThat(testTravel.getHowToContactDescription()).isEqualTo(DEFAULT_HOW_TO_CONTACT_DESCRIPTION);
        assertThat(testTravel.getComplementaryRules()).isEqualTo(DEFAULT_COMPLEMENTARY_RULES);
        assertThat(testTravel.isBookable()).isEqualTo(DEFAULT_BOOKABLE);
        assertThat(testTravel.getAccessCode()).isEqualTo(DEFAULT_ACCESS_CODE);
    }

    @Test
    @Transactional
    public void createTravelWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = travelRepository.findAll().size();

        // Create the Travel with an existing ID
        travel.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTravelMockMvc.perform(post("/api/travels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(travel)))
            .andExpect(status().isBadRequest());

        // Validate the Travel in the database
        List<Travel> travelList = travelRepository.findAll();
        assertThat(travelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTravels() throws Exception {
        // Initialize the database
        travelRepository.saveAndFlush(travel);

        // Get all the travelList
        restTravelMockMvc.perform(get("/api/travels?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(travel.getId().intValue())))
            .andExpect(jsonPath("$.[*].departureDate").value(hasItem(DEFAULT_DEPARTURE_DATE.toString())))
            .andExpect(jsonPath("$.[*].arrivalDate").value(hasItem(DEFAULT_ARRIVAL_DATE.toString())))
            .andExpect(jsonPath("$.[*].departureCity").value(hasItem(DEFAULT_DEPARTURE_CITY.toString())))
            .andExpect(jsonPath("$.[*].arrivalCity").value(hasItem(DEFAULT_ARRIVAL_CITY.toString())))
            .andExpect(jsonPath("$.[*].availableKGs").value(hasItem(DEFAULT_AVAILABLE_K_GS.doubleValue())))
            .andExpect(jsonPath("$.[*].pricePerKG").value(hasItem(DEFAULT_PRICE_PER_KG.doubleValue())))
            .andExpect(jsonPath("$.[*].travelMode").value(hasItem(DEFAULT_TRAVEL_MODE.toString())))
            .andExpect(jsonPath("$.[*].deleveryMode").value(hasItem(DEFAULT_DELEVERY_MODE.toString())))
            .andExpect(jsonPath("$.[*].howToContactDescription").value(hasItem(DEFAULT_HOW_TO_CONTACT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].complementaryRules").value(hasItem(DEFAULT_COMPLEMENTARY_RULES.toString())))
            .andExpect(jsonPath("$.[*].bookable").value(hasItem(DEFAULT_BOOKABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].accessCode").value(hasItem(DEFAULT_ACCESS_CODE.toString())));
    }
    
    @Test
    @Transactional
    public void getTravel() throws Exception {
        // Initialize the database
        travelRepository.saveAndFlush(travel);

        // Get the travel
        restTravelMockMvc.perform(get("/api/travels/{id}", travel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(travel.getId().intValue()))
            .andExpect(jsonPath("$.departureDate").value(DEFAULT_DEPARTURE_DATE.toString()))
            .andExpect(jsonPath("$.arrivalDate").value(DEFAULT_ARRIVAL_DATE.toString()))
            .andExpect(jsonPath("$.departureCity").value(DEFAULT_DEPARTURE_CITY.toString()))
            .andExpect(jsonPath("$.arrivalCity").value(DEFAULT_ARRIVAL_CITY.toString()))
            .andExpect(jsonPath("$.availableKGs").value(DEFAULT_AVAILABLE_K_GS.doubleValue()))
            .andExpect(jsonPath("$.pricePerKG").value(DEFAULT_PRICE_PER_KG.doubleValue()))
            .andExpect(jsonPath("$.travelMode").value(DEFAULT_TRAVEL_MODE.toString()))
            .andExpect(jsonPath("$.deleveryMode").value(DEFAULT_DELEVERY_MODE.toString()))
            .andExpect(jsonPath("$.howToContactDescription").value(DEFAULT_HOW_TO_CONTACT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.complementaryRules").value(DEFAULT_COMPLEMENTARY_RULES.toString()))
            .andExpect(jsonPath("$.bookable").value(DEFAULT_BOOKABLE.booleanValue()))
            .andExpect(jsonPath("$.accessCode").value(DEFAULT_ACCESS_CODE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTravel() throws Exception {
        // Get the travel
        restTravelMockMvc.perform(get("/api/travels/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTravel() throws Exception {
        // Initialize the database
        travelRepository.saveAndFlush(travel);

        int databaseSizeBeforeUpdate = travelRepository.findAll().size();

        // Update the travel
        Travel updatedTravel = travelRepository.findById(travel.getId()).get();
        // Disconnect from session so that the updates on updatedTravel are not directly saved in db
        em.detach(updatedTravel);
        updatedTravel
            .departureDate(UPDATED_DEPARTURE_DATE)
            .arrivalDate(UPDATED_ARRIVAL_DATE)
            .departureCity(UPDATED_DEPARTURE_CITY)
            .arrivalCity(UPDATED_ARRIVAL_CITY)
            .availableKGs(UPDATED_AVAILABLE_K_GS)
            .pricePerKG(UPDATED_PRICE_PER_KG)
            .travelMode(UPDATED_TRAVEL_MODE)
            .deleveryMode(UPDATED_DELEVERY_MODE)
            .howToContactDescription(UPDATED_HOW_TO_CONTACT_DESCRIPTION)
            .complementaryRules(UPDATED_COMPLEMENTARY_RULES)
            .bookable(UPDATED_BOOKABLE)
            .accessCode(UPDATED_ACCESS_CODE);

        restTravelMockMvc.perform(put("/api/travels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTravel)))
            .andExpect(status().isOk());

        // Validate the Travel in the database
        List<Travel> travelList = travelRepository.findAll();
        assertThat(travelList).hasSize(databaseSizeBeforeUpdate);
        Travel testTravel = travelList.get(travelList.size() - 1);
        assertThat(testTravel.getDepartureDate()).isEqualTo(UPDATED_DEPARTURE_DATE);
        assertThat(testTravel.getArrivalDate()).isEqualTo(UPDATED_ARRIVAL_DATE);
        assertThat(testTravel.getDepartureCity()).isEqualTo(UPDATED_DEPARTURE_CITY);
        assertThat(testTravel.getArrivalCity()).isEqualTo(UPDATED_ARRIVAL_CITY);
        assertThat(testTravel.getAvailableKGs()).isEqualTo(UPDATED_AVAILABLE_K_GS);
        assertThat(testTravel.getPricePerKG()).isEqualTo(UPDATED_PRICE_PER_KG);
        assertThat(testTravel.getTravelMode()).isEqualTo(UPDATED_TRAVEL_MODE);
        assertThat(testTravel.getDeleveryMode()).isEqualTo(UPDATED_DELEVERY_MODE);
        assertThat(testTravel.getHowToContactDescription()).isEqualTo(UPDATED_HOW_TO_CONTACT_DESCRIPTION);
        assertThat(testTravel.getComplementaryRules()).isEqualTo(UPDATED_COMPLEMENTARY_RULES);
        assertThat(testTravel.isBookable()).isEqualTo(UPDATED_BOOKABLE);
        assertThat(testTravel.getAccessCode()).isEqualTo(UPDATED_ACCESS_CODE);
    }

    @Test
    @Transactional
    public void updateNonExistingTravel() throws Exception {
        int databaseSizeBeforeUpdate = travelRepository.findAll().size();

        // Create the Travel

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTravelMockMvc.perform(put("/api/travels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(travel)))
            .andExpect(status().isBadRequest());

        // Validate the Travel in the database
        List<Travel> travelList = travelRepository.findAll();
        assertThat(travelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTravel() throws Exception {
        // Initialize the database
        travelRepository.saveAndFlush(travel);

        int databaseSizeBeforeDelete = travelRepository.findAll().size();

        // Get the travel
        restTravelMockMvc.perform(delete("/api/travels/{id}", travel.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Travel> travelList = travelRepository.findAll();
        assertThat(travelList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Travel.class);
        Travel travel1 = new Travel();
        travel1.setId(1L);
        Travel travel2 = new Travel();
        travel2.setId(travel1.getId());
        assertThat(travel1).isEqualTo(travel2);
        travel2.setId(2L);
        assertThat(travel1).isNotEqualTo(travel2);
        travel1.setId(null);
        assertThat(travel1).isNotEqualTo(travel2);
    }
}
