package com.klav.web.rest;

import com.klav.KlavApp;

import com.klav.domain.PackageType;
import com.klav.repository.PackageTypeRepository;
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

/**
 * Test class for the PackageTypeResource REST controller.
 *
 * @see PackageTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = KlavApp.class)
public class PackageTypeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private PackageTypeRepository packageTypeRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPackageTypeMockMvc;

    private PackageType packageType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PackageTypeResource packageTypeResource = new PackageTypeResource(packageTypeRepository);
        this.restPackageTypeMockMvc = MockMvcBuilders.standaloneSetup(packageTypeResource)
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
    public static PackageType createEntity(EntityManager em) {
        PackageType packageType = new PackageType()
            .name(DEFAULT_NAME);
        return packageType;
    }

    @Before
    public void initTest() {
        packageType = createEntity(em);
    }

    @Test
    @Transactional
    public void createPackageType() throws Exception {
        int databaseSizeBeforeCreate = packageTypeRepository.findAll().size();

        // Create the PackageType
        restPackageTypeMockMvc.perform(post("/api/package-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(packageType)))
            .andExpect(status().isCreated());

        // Validate the PackageType in the database
        List<PackageType> packageTypeList = packageTypeRepository.findAll();
        assertThat(packageTypeList).hasSize(databaseSizeBeforeCreate + 1);
        PackageType testPackageType = packageTypeList.get(packageTypeList.size() - 1);
        assertThat(testPackageType.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createPackageTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = packageTypeRepository.findAll().size();

        // Create the PackageType with an existing ID
        packageType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPackageTypeMockMvc.perform(post("/api/package-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(packageType)))
            .andExpect(status().isBadRequest());

        // Validate the PackageType in the database
        List<PackageType> packageTypeList = packageTypeRepository.findAll();
        assertThat(packageTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPackageTypes() throws Exception {
        // Initialize the database
        packageTypeRepository.saveAndFlush(packageType);

        // Get all the packageTypeList
        restPackageTypeMockMvc.perform(get("/api/package-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(packageType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getPackageType() throws Exception {
        // Initialize the database
        packageTypeRepository.saveAndFlush(packageType);

        // Get the packageType
        restPackageTypeMockMvc.perform(get("/api/package-types/{id}", packageType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(packageType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPackageType() throws Exception {
        // Get the packageType
        restPackageTypeMockMvc.perform(get("/api/package-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePackageType() throws Exception {
        // Initialize the database
        packageTypeRepository.saveAndFlush(packageType);

        int databaseSizeBeforeUpdate = packageTypeRepository.findAll().size();

        // Update the packageType
        PackageType updatedPackageType = packageTypeRepository.findById(packageType.getId()).get();
        // Disconnect from session so that the updates on updatedPackageType are not directly saved in db
        em.detach(updatedPackageType);
        updatedPackageType
            .name(UPDATED_NAME);

        restPackageTypeMockMvc.perform(put("/api/package-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPackageType)))
            .andExpect(status().isOk());

        // Validate the PackageType in the database
        List<PackageType> packageTypeList = packageTypeRepository.findAll();
        assertThat(packageTypeList).hasSize(databaseSizeBeforeUpdate);
        PackageType testPackageType = packageTypeList.get(packageTypeList.size() - 1);
        assertThat(testPackageType.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingPackageType() throws Exception {
        int databaseSizeBeforeUpdate = packageTypeRepository.findAll().size();

        // Create the PackageType

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPackageTypeMockMvc.perform(put("/api/package-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(packageType)))
            .andExpect(status().isBadRequest());

        // Validate the PackageType in the database
        List<PackageType> packageTypeList = packageTypeRepository.findAll();
        assertThat(packageTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePackageType() throws Exception {
        // Initialize the database
        packageTypeRepository.saveAndFlush(packageType);

        int databaseSizeBeforeDelete = packageTypeRepository.findAll().size();

        // Get the packageType
        restPackageTypeMockMvc.perform(delete("/api/package-types/{id}", packageType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PackageType> packageTypeList = packageTypeRepository.findAll();
        assertThat(packageTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PackageType.class);
        PackageType packageType1 = new PackageType();
        packageType1.setId(1L);
        PackageType packageType2 = new PackageType();
        packageType2.setId(packageType1.getId());
        assertThat(packageType1).isEqualTo(packageType2);
        packageType2.setId(2L);
        assertThat(packageType1).isNotEqualTo(packageType2);
        packageType1.setId(null);
        assertThat(packageType1).isNotEqualTo(packageType2);
    }
}
