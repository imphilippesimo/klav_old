package com.klav.web.rest;

import com.klav.KlavApp;

import com.klav.domain.KlavUser;
import com.klav.repository.KlavUserRepository;
import com.klav.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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
import java.util.ArrayList;
import java.util.List;


import static com.klav.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the KlavUserResource REST controller.
 *
 * @see KlavUserResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = KlavApp.class)
public class KlavUserResourceIntTest {

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final Instant DEFAULT_BIRTHDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_BIRTHDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_SELF_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_SELF_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_GENDER = "AAAAAAAAAA";
    private static final String UPDATED_GENDER = "BBBBBBBBBB";

    private static final String DEFAULT_NATIONALITY = "AAAAAAAAAA";
    private static final String UPDATED_NATIONALITY = "BBBBBBBBBB";

    private static final String DEFAULT_LOGIN = "AAAAAAAAAA";
    private static final String UPDATED_LOGIN = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVATED = false;
    private static final Boolean UPDATED_ACTIVATED = true;

    private static final String DEFAULT_ACTIVATION_KEY = "AAAAAAAAAA";
    private static final String UPDATED_ACTIVATION_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_RESET_KEY = "AAAAAAAAAA";
    private static final String UPDATED_RESET_KEY = "BBBBBBBBBB";

    private static final Instant DEFAULT_RESET_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_RESET_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    @Autowired
    private KlavUserRepository klavUserRepository;

    @Mock
    private KlavUserRepository klavUserRepositoryMock;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restKlavUserMockMvc;

    private KlavUser klavUser;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final KlavUserResource klavUserResource = new KlavUserResource(klavUserRepository);
        this.restKlavUserMockMvc = MockMvcBuilders.standaloneSetup(klavUserResource)
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
    public static KlavUser createEntity(EntityManager em) {
        KlavUser klavUser = new KlavUser()
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .birthdate(DEFAULT_BIRTHDATE)
            .selfDescription(DEFAULT_SELF_DESCRIPTION)
            .gender(DEFAULT_GENDER)
            .nationality(DEFAULT_NATIONALITY)
            .login(DEFAULT_LOGIN)
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .email(DEFAULT_EMAIL)
            .activated(DEFAULT_ACTIVATED)
            .activationKey(DEFAULT_ACTIVATION_KEY)
            .resetKey(DEFAULT_RESET_KEY)
            .resetDate(DEFAULT_RESET_DATE)
            .password(DEFAULT_PASSWORD);
        return klavUser;
    }

    @Before
    public void initTest() {
        klavUser = createEntity(em);
    }

    @Test
    @Transactional
    public void createKlavUser() throws Exception {
        int databaseSizeBeforeCreate = klavUserRepository.findAll().size();

        // Create the KlavUser
        restKlavUserMockMvc.perform(post("/api/klav-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(klavUser)))
            .andExpect(status().isCreated());

        // Validate the KlavUser in the database
        List<KlavUser> klavUserList = klavUserRepository.findAll();
        assertThat(klavUserList).hasSize(databaseSizeBeforeCreate + 1);
        KlavUser testKlavUser = klavUserList.get(klavUserList.size() - 1);
        assertThat(testKlavUser.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testKlavUser.getBirthdate()).isEqualTo(DEFAULT_BIRTHDATE);
        assertThat(testKlavUser.getSelfDescription()).isEqualTo(DEFAULT_SELF_DESCRIPTION);
        assertThat(testKlavUser.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testKlavUser.getNationality()).isEqualTo(DEFAULT_NATIONALITY);
        assertThat(testKlavUser.getLogin()).isEqualTo(DEFAULT_LOGIN);
        assertThat(testKlavUser.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testKlavUser.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testKlavUser.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testKlavUser.isActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testKlavUser.getActivationKey()).isEqualTo(DEFAULT_ACTIVATION_KEY);
        assertThat(testKlavUser.getResetKey()).isEqualTo(DEFAULT_RESET_KEY);
        assertThat(testKlavUser.getResetDate()).isEqualTo(DEFAULT_RESET_DATE);
        assertThat(testKlavUser.getPassword()).isEqualTo(DEFAULT_PASSWORD);
    }

    @Test
    @Transactional
    public void createKlavUserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = klavUserRepository.findAll().size();

        // Create the KlavUser with an existing ID
        klavUser.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restKlavUserMockMvc.perform(post("/api/klav-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(klavUser)))
            .andExpect(status().isBadRequest());

        // Validate the KlavUser in the database
        List<KlavUser> klavUserList = klavUserRepository.findAll();
        assertThat(klavUserList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllKlavUsers() throws Exception {
        // Initialize the database
        klavUserRepository.saveAndFlush(klavUser);

        // Get all the klavUserList
        restKlavUserMockMvc.perform(get("/api/klav-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(klavUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].birthdate").value(hasItem(DEFAULT_BIRTHDATE.toString())))
            .andExpect(jsonPath("$.[*].selfDescription").value(hasItem(DEFAULT_SELF_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].nationality").value(hasItem(DEFAULT_NATIONALITY.toString())))
            .andExpect(jsonPath("$.[*].login").value(hasItem(DEFAULT_LOGIN.toString())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].activationKey").value(hasItem(DEFAULT_ACTIVATION_KEY.toString())))
            .andExpect(jsonPath("$.[*].resetKey").value(hasItem(DEFAULT_RESET_KEY.toString())))
            .andExpect(jsonPath("$.[*].resetDate").value(hasItem(DEFAULT_RESET_DATE.toString())))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD.toString())));
    }
    
    public void getAllKlavUsersWithEagerRelationshipsIsEnabled() throws Exception {
        KlavUserResource klavUserResource = new KlavUserResource(klavUserRepositoryMock);
        when(klavUserRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restKlavUserMockMvc = MockMvcBuilders.standaloneSetup(klavUserResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restKlavUserMockMvc.perform(get("/api/klav-users?eagerload=true"))
        .andExpect(status().isOk());

        verify(klavUserRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    public void getAllKlavUsersWithEagerRelationshipsIsNotEnabled() throws Exception {
        KlavUserResource klavUserResource = new KlavUserResource(klavUserRepositoryMock);
            when(klavUserRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restKlavUserMockMvc = MockMvcBuilders.standaloneSetup(klavUserResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restKlavUserMockMvc.perform(get("/api/klav-users?eagerload=true"))
        .andExpect(status().isOk());

            verify(klavUserRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getKlavUser() throws Exception {
        // Initialize the database
        klavUserRepository.saveAndFlush(klavUser);

        // Get the klavUser
        restKlavUserMockMvc.perform(get("/api/klav-users/{id}", klavUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(klavUser.getId().intValue()))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER.toString()))
            .andExpect(jsonPath("$.birthdate").value(DEFAULT_BIRTHDATE.toString()))
            .andExpect(jsonPath("$.selfDescription").value(DEFAULT_SELF_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.nationality").value(DEFAULT_NATIONALITY.toString()))
            .andExpect(jsonPath("$.login").value(DEFAULT_LOGIN.toString()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.activated").value(DEFAULT_ACTIVATED.booleanValue()))
            .andExpect(jsonPath("$.activationKey").value(DEFAULT_ACTIVATION_KEY.toString()))
            .andExpect(jsonPath("$.resetKey").value(DEFAULT_RESET_KEY.toString()))
            .andExpect(jsonPath("$.resetDate").value(DEFAULT_RESET_DATE.toString()))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingKlavUser() throws Exception {
        // Get the klavUser
        restKlavUserMockMvc.perform(get("/api/klav-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateKlavUser() throws Exception {
        // Initialize the database
        klavUserRepository.saveAndFlush(klavUser);

        int databaseSizeBeforeUpdate = klavUserRepository.findAll().size();

        // Update the klavUser
        KlavUser updatedKlavUser = klavUserRepository.findById(klavUser.getId()).get();
        // Disconnect from session so that the updates on updatedKlavUser are not directly saved in db
        em.detach(updatedKlavUser);
        updatedKlavUser
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .birthdate(UPDATED_BIRTHDATE)
            .selfDescription(UPDATED_SELF_DESCRIPTION)
            .gender(UPDATED_GENDER)
            .nationality(UPDATED_NATIONALITY)
            .login(UPDATED_LOGIN)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .email(UPDATED_EMAIL)
            .activated(UPDATED_ACTIVATED)
            .activationKey(UPDATED_ACTIVATION_KEY)
            .resetKey(UPDATED_RESET_KEY)
            .resetDate(UPDATED_RESET_DATE)
            .password(UPDATED_PASSWORD);

        restKlavUserMockMvc.perform(put("/api/klav-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedKlavUser)))
            .andExpect(status().isOk());

        // Validate the KlavUser in the database
        List<KlavUser> klavUserList = klavUserRepository.findAll();
        assertThat(klavUserList).hasSize(databaseSizeBeforeUpdate);
        KlavUser testKlavUser = klavUserList.get(klavUserList.size() - 1);
        assertThat(testKlavUser.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testKlavUser.getBirthdate()).isEqualTo(UPDATED_BIRTHDATE);
        assertThat(testKlavUser.getSelfDescription()).isEqualTo(UPDATED_SELF_DESCRIPTION);
        assertThat(testKlavUser.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testKlavUser.getNationality()).isEqualTo(UPDATED_NATIONALITY);
        assertThat(testKlavUser.getLogin()).isEqualTo(UPDATED_LOGIN);
        assertThat(testKlavUser.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testKlavUser.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testKlavUser.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testKlavUser.isActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testKlavUser.getActivationKey()).isEqualTo(UPDATED_ACTIVATION_KEY);
        assertThat(testKlavUser.getResetKey()).isEqualTo(UPDATED_RESET_KEY);
        assertThat(testKlavUser.getResetDate()).isEqualTo(UPDATED_RESET_DATE);
        assertThat(testKlavUser.getPassword()).isEqualTo(UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    public void updateNonExistingKlavUser() throws Exception {
        int databaseSizeBeforeUpdate = klavUserRepository.findAll().size();

        // Create the KlavUser

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKlavUserMockMvc.perform(put("/api/klav-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(klavUser)))
            .andExpect(status().isBadRequest());

        // Validate the KlavUser in the database
        List<KlavUser> klavUserList = klavUserRepository.findAll();
        assertThat(klavUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteKlavUser() throws Exception {
        // Initialize the database
        klavUserRepository.saveAndFlush(klavUser);

        int databaseSizeBeforeDelete = klavUserRepository.findAll().size();

        // Get the klavUser
        restKlavUserMockMvc.perform(delete("/api/klav-users/{id}", klavUser.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<KlavUser> klavUserList = klavUserRepository.findAll();
        assertThat(klavUserList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(KlavUser.class);
        KlavUser klavUser1 = new KlavUser();
        klavUser1.setId(1L);
        KlavUser klavUser2 = new KlavUser();
        klavUser2.setId(klavUser1.getId());
        assertThat(klavUser1).isEqualTo(klavUser2);
        klavUser2.setId(2L);
        assertThat(klavUser1).isNotEqualTo(klavUser2);
        klavUser1.setId(null);
        assertThat(klavUser1).isNotEqualTo(klavUser2);
    }
}
