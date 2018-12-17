package com.klav.web.rest;

import com.klav.KlavApp;

import com.klav.domain.TrustIndex;
import com.klav.repository.TrustIndexRepository;
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

/**
 * Test class for the TrustIndexResource REST controller.
 *
 * @see TrustIndexResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = KlavApp.class)
public class TrustIndexResourceIntTest {

    private static final Float DEFAULT_VALUE = 1F;
    private static final Float UPDATED_VALUE = 2F;

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private TrustIndexRepository trustIndexRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTrustIndexMockMvc;

    private TrustIndex trustIndex;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TrustIndexResource trustIndexResource = new TrustIndexResource(trustIndexRepository);
        this.restTrustIndexMockMvc = MockMvcBuilders.standaloneSetup(trustIndexResource)
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
    public static TrustIndex createEntity(EntityManager em) {
        TrustIndex trustIndex = new TrustIndex()
            .value(DEFAULT_VALUE)
            .date(DEFAULT_DATE);
        return trustIndex;
    }

    @Before
    public void initTest() {
        trustIndex = createEntity(em);
    }

    @Test
    @Transactional
    public void createTrustIndex() throws Exception {
        int databaseSizeBeforeCreate = trustIndexRepository.findAll().size();

        // Create the TrustIndex
        restTrustIndexMockMvc.perform(post("/api/trust-indices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trustIndex)))
            .andExpect(status().isCreated());

        // Validate the TrustIndex in the database
        List<TrustIndex> trustIndexList = trustIndexRepository.findAll();
        assertThat(trustIndexList).hasSize(databaseSizeBeforeCreate + 1);
        TrustIndex testTrustIndex = trustIndexList.get(trustIndexList.size() - 1);
        assertThat(testTrustIndex.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testTrustIndex.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void createTrustIndexWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = trustIndexRepository.findAll().size();

        // Create the TrustIndex with an existing ID
        trustIndex.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTrustIndexMockMvc.perform(post("/api/trust-indices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trustIndex)))
            .andExpect(status().isBadRequest());

        // Validate the TrustIndex in the database
        List<TrustIndex> trustIndexList = trustIndexRepository.findAll();
        assertThat(trustIndexList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTrustIndices() throws Exception {
        // Initialize the database
        trustIndexRepository.saveAndFlush(trustIndex);

        // Get all the trustIndexList
        restTrustIndexMockMvc.perform(get("/api/trust-indices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(trustIndex.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getTrustIndex() throws Exception {
        // Initialize the database
        trustIndexRepository.saveAndFlush(trustIndex);

        // Get the trustIndex
        restTrustIndexMockMvc.perform(get("/api/trust-indices/{id}", trustIndex.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(trustIndex.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.doubleValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTrustIndex() throws Exception {
        // Get the trustIndex
        restTrustIndexMockMvc.perform(get("/api/trust-indices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTrustIndex() throws Exception {
        // Initialize the database
        trustIndexRepository.saveAndFlush(trustIndex);

        int databaseSizeBeforeUpdate = trustIndexRepository.findAll().size();

        // Update the trustIndex
        TrustIndex updatedTrustIndex = trustIndexRepository.findById(trustIndex.getId()).get();
        // Disconnect from session so that the updates on updatedTrustIndex are not directly saved in db
        em.detach(updatedTrustIndex);
        updatedTrustIndex
            .value(UPDATED_VALUE)
            .date(UPDATED_DATE);

        restTrustIndexMockMvc.perform(put("/api/trust-indices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTrustIndex)))
            .andExpect(status().isOk());

        // Validate the TrustIndex in the database
        List<TrustIndex> trustIndexList = trustIndexRepository.findAll();
        assertThat(trustIndexList).hasSize(databaseSizeBeforeUpdate);
        TrustIndex testTrustIndex = trustIndexList.get(trustIndexList.size() - 1);
        assertThat(testTrustIndex.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testTrustIndex.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingTrustIndex() throws Exception {
        int databaseSizeBeforeUpdate = trustIndexRepository.findAll().size();

        // Create the TrustIndex

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTrustIndexMockMvc.perform(put("/api/trust-indices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trustIndex)))
            .andExpect(status().isBadRequest());

        // Validate the TrustIndex in the database
        List<TrustIndex> trustIndexList = trustIndexRepository.findAll();
        assertThat(trustIndexList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTrustIndex() throws Exception {
        // Initialize the database
        trustIndexRepository.saveAndFlush(trustIndex);

        int databaseSizeBeforeDelete = trustIndexRepository.findAll().size();

        // Get the trustIndex
        restTrustIndexMockMvc.perform(delete("/api/trust-indices/{id}", trustIndex.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TrustIndex> trustIndexList = trustIndexRepository.findAll();
        assertThat(trustIndexList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TrustIndex.class);
        TrustIndex trustIndex1 = new TrustIndex();
        trustIndex1.setId(1L);
        TrustIndex trustIndex2 = new TrustIndex();
        trustIndex2.setId(trustIndex1.getId());
        assertThat(trustIndex1).isEqualTo(trustIndex2);
        trustIndex2.setId(2L);
        assertThat(trustIndex1).isNotEqualTo(trustIndex2);
        trustIndex1.setId(null);
        assertThat(trustIndex1).isNotEqualTo(trustIndex2);
    }
}
