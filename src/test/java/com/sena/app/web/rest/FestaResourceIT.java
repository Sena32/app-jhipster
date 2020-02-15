package com.sena.app.web.rest;

import com.sena.app.JhipsterApp;
import com.sena.app.domain.Festa;
import com.sena.app.repository.FestaRepository;
import com.sena.app.service.FestaService;
import com.sena.app.service.dto.FestaDTO;
import com.sena.app.service.mapper.FestaMapper;
import com.sena.app.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.sena.app.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link FestaResource} REST controller.
 */
@SpringBootTest(classes = JhipsterApp.class)
public class FestaResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private FestaRepository festaRepository;

    @Autowired
    private FestaMapper festaMapper;

    @Autowired
    private FestaService festaService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restFestaMockMvc;

    private Festa festa;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FestaResource festaResource = new FestaResource(festaService);
        this.restFestaMockMvc = MockMvcBuilders.standaloneSetup(festaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Festa createEntity(EntityManager em) {
        Festa festa = new Festa()
            .name(DEFAULT_NAME);
        return festa;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Festa createUpdatedEntity(EntityManager em) {
        Festa festa = new Festa()
            .name(UPDATED_NAME);
        return festa;
    }

    @BeforeEach
    public void initTest() {
        festa = createEntity(em);
    }

    @Test
    @Transactional
    public void getAllFestas() throws Exception {
        // Initialize the database
        festaRepository.saveAndFlush(festa);

        // Get all the festaList
        restFestaMockMvc.perform(get("/api/festas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(festa.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getFesta() throws Exception {
        // Initialize the database
        festaRepository.saveAndFlush(festa);

        // Get the festa
        restFestaMockMvc.perform(get("/api/festas/{id}", festa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(festa.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    public void getNonExistingFesta() throws Exception {
        // Get the festa
        restFestaMockMvc.perform(get("/api/festas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }
}
