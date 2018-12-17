package com.klav.web.rest;

import com.klav.KlavApp;

import com.klav.domain.TravelPackage;
import com.klav.repository.TravelPackageRepository;
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
import java.util.List;


import static com.klav.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.klav.domain.enumeration.DeliveryMode;
/**
 * Test class for the TravelPackageResource REST controller.
 *
 * @see TravelPackageResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = KlavApp.class)
public class TravelPackageResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final Double DEFAULT_WEIGHT = 1D;
    private static final Double UPDATED_WEIGHT = 2D;

    private static final String DEFAULT_ACCESS_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ACCESS_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DELEVERY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_DELEVERY_CODE = "BBBBBBBBBB";

    private static final DeliveryMode DEFAULT_DESIRED_DELEVERY_MODE = DeliveryMode.HOMEDELEVERY;
    private static final DeliveryMode UPDATED_DESIRED_DELEVERY_MODE = DeliveryMode.HOMEWITHDRAWAL;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Float DEFAULT_PRICE_PER_KG = 1F;
    private static final Float UPDATED_PRICE_PER_KG = 2F;

    private static final Boolean DEFAULT_FRAGILE = false;
    private static final Boolean UPDATED_FRAGILE = true;

    @Autowired
    private TravelPackageRepository travelPackageRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTravelPackageMockMvc;

    private TravelPackage travelPackage;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TravelPackageResource travelPackageResource = new TravelPackageResource(travelPackageRepository);
        this.restTravelPackageMockMvc = MockMvcBuilders.standaloneSetup(travelPackageResource)
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
    public static TravelPackage createEntity(EntityManager em) {
        TravelPackage travelPackage = new TravelPackage()
            .title(DEFAULT_TITLE)
            .weight(DEFAULT_WEIGHT)
            .accessCode(DEFAULT_ACCESS_CODE)
            .deleveryCode(DEFAULT_DELEVERY_CODE)
            .desiredDeleveryMode(DEFAULT_DESIRED_DELEVERY_MODE)
            .description(DEFAULT_DESCRIPTION)
            .pricePerKG(DEFAULT_PRICE_PER_KG)
            .fragile(DEFAULT_FRAGILE);
        return travelPackage;
    }

    @Before
    public void initTest() {
        travelPackage = createEntity(em);
    }

    @Test
    @Transactional
    public void createTravelPackage() throws Exception {
        int databaseSizeBeforeCreate = travelPackageRepository.findAll().size();

        // Create the TravelPackage
        restTravelPackageMockMvc.perform(post("/api/travel-packages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(travelPackage)))
            .andExpect(status().isCreated());

        // Validate the TravelPackage in the database
        List<TravelPackage> travelPackageList = travelPackageRepository.findAll();
        assertThat(travelPackageList).hasSize(databaseSizeBeforeCreate + 1);
        TravelPackage testTravelPackage = travelPackageList.get(travelPackageList.size() - 1);
        assertThat(testTravelPackage.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testTravelPackage.getWeight()).isEqualTo(DEFAULT_WEIGHT);
        assertThat(testTravelPackage.getAccessCode()).isEqualTo(DEFAULT_ACCESS_CODE);
        assertThat(testTravelPackage.getDeleveryCode()).isEqualTo(DEFAULT_DELEVERY_CODE);
        assertThat(testTravelPackage.getDesiredDeleveryMode()).isEqualTo(DEFAULT_DESIRED_DELEVERY_MODE);
        assertThat(testTravelPackage.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTravelPackage.getPricePerKG()).isEqualTo(DEFAULT_PRICE_PER_KG);
        assertThat(testTravelPackage.isFragile()).isEqualTo(DEFAULT_FRAGILE);
    }

    @Test
    @Transactional
    public void createTravelPackageWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = travelPackageRepository.findAll().size();

        // Create the TravelPackage with an existing ID
        travelPackage.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTravelPackageMockMvc.perform(post("/api/travel-packages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(travelPackage)))
            .andExpect(status().isBadRequest());

        // Validate the TravelPackage in the database
        List<TravelPackage> travelPackageList = travelPackageRepository.findAll();
        assertThat(travelPackageList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTravelPackages() throws Exception {
        // Initialize the database
        travelPackageRepository.saveAndFlush(travelPackage);

        // Get all the travelPackageList
        restTravelPackageMockMvc.perform(get("/api/travel-packages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(travelPackage.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].accessCode").value(hasItem(DEFAULT_ACCESS_CODE.toString())))
            .andExpect(jsonPath("$.[*].deleveryCode").value(hasItem(DEFAULT_DELEVERY_CODE.toString())))
            .andExpect(jsonPath("$.[*].desiredDeleveryMode").value(hasItem(DEFAULT_DESIRED_DELEVERY_MODE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].pricePerKG").value(hasItem(DEFAULT_PRICE_PER_KG.doubleValue())))
            .andExpect(jsonPath("$.[*].fragile").value(hasItem(DEFAULT_FRAGILE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getTravelPackage() throws Exception {
        // Initialize the database
        travelPackageRepository.saveAndFlush(travelPackage);

        // Get the travelPackage
        restTravelPackageMockMvc.perform(get("/api/travel-packages/{id}", travelPackage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(travelPackage.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.weight").value(DEFAULT_WEIGHT.doubleValue()))
            .andExpect(jsonPath("$.accessCode").value(DEFAULT_ACCESS_CODE.toString()))
            .andExpect(jsonPath("$.deleveryCode").value(DEFAULT_DELEVERY_CODE.toString()))
            .andExpect(jsonPath("$.desiredDeleveryMode").value(DEFAULT_DESIRED_DELEVERY_MODE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.pricePerKG").value(DEFAULT_PRICE_PER_KG.doubleValue()))
            .andExpect(jsonPath("$.fragile").value(DEFAULT_FRAGILE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTravelPackage() throws Exception {
        // Get the travelPackage
        restTravelPackageMockMvc.perform(get("/api/travel-packages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTravelPackage() throws Exception {
        // Initialize the database
        travelPackageRepository.saveAndFlush(travelPackage);

        int databaseSizeBeforeUpdate = travelPackageRepository.findAll().size();

        // Update the travelPackage
        TravelPackage updatedTravelPackage = travelPackageRepository.findById(travelPackage.getId()).get();
        // Disconnect from session so that the updates on updatedTravelPackage are not directly saved in db
        em.detach(updatedTravelPackage);
        updatedTravelPackage
            .title(UPDATED_TITLE)
            .weight(UPDATED_WEIGHT)
            .accessCode(UPDATED_ACCESS_CODE)
            .deleveryCode(UPDATED_DELEVERY_CODE)
            .desiredDeleveryMode(UPDATED_DESIRED_DELEVERY_MODE)
            .description(UPDATED_DESCRIPTION)
            .pricePerKG(UPDATED_PRICE_PER_KG)
            .fragile(UPDATED_FRAGILE);

        restTravelPackageMockMvc.perform(put("/api/travel-packages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTravelPackage)))
            .andExpect(status().isOk());

        // Validate the TravelPackage in the database
        List<TravelPackage> travelPackageList = travelPackageRepository.findAll();
        assertThat(travelPackageList).hasSize(databaseSizeBeforeUpdate);
        TravelPackage testTravelPackage = travelPackageList.get(travelPackageList.size() - 1);
        assertThat(testTravelPackage.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testTravelPackage.getWeight()).isEqualTo(UPDATED_WEIGHT);
        assertThat(testTravelPackage.getAccessCode()).isEqualTo(UPDATED_ACCESS_CODE);
        assertThat(testTravelPackage.getDeleveryCode()).isEqualTo(UPDATED_DELEVERY_CODE);
        assertThat(testTravelPackage.getDesiredDeleveryMode()).isEqualTo(UPDATED_DESIRED_DELEVERY_MODE);
        assertThat(testTravelPackage.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTravelPackage.getPricePerKG()).isEqualTo(UPDATED_PRICE_PER_KG);
        assertThat(testTravelPackage.isFragile()).isEqualTo(UPDATED_FRAGILE);
    }

    @Test
    @Transactional
    public void updateNonExistingTravelPackage() throws Exception {
        int databaseSizeBeforeUpdate = travelPackageRepository.findAll().size();

        // Create the TravelPackage

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTravelPackageMockMvc.perform(put("/api/travel-packages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(travelPackage)))
            .andExpect(status().isBadRequest());

        // Validate the TravelPackage in the database
        List<TravelPackage> travelPackageList = travelPackageRepository.findAll();
        assertThat(travelPackageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTravelPackage() throws Exception {
        // Initialize the database
        travelPackageRepository.saveAndFlush(travelPackage);

        int databaseSizeBeforeDelete = travelPackageRepository.findAll().size();

        // Get the travelPackage
        restTravelPackageMockMvc.perform(delete("/api/travel-packages/{id}", travelPackage.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TravelPackage> travelPackageList = travelPackageRepository.findAll();
        assertThat(travelPackageList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TravelPackage.class);
        TravelPackage travelPackage1 = new TravelPackage();
        travelPackage1.setId(1L);
        TravelPackage travelPackage2 = new TravelPackage();
        travelPackage2.setId(travelPackage1.getId());
        assertThat(travelPackage1).isEqualTo(travelPackage2);
        travelPackage2.setId(2L);
        assertThat(travelPackage1).isNotEqualTo(travelPackage2);
        travelPackage1.setId(null);
        assertThat(travelPackage1).isNotEqualTo(travelPackage2);
    }
}
