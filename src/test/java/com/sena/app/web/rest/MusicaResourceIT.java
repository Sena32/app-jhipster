package com.sena.app.web.rest;

import com.sena.app.JhipsterApp;
import com.sena.app.domain.Musica;
import com.sena.app.repository.MusicaRepository;
import com.sena.app.service.MusicaService;
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
 * Integration tests for the {@link MusicaResource} REST controller.
 */
@SpringBootTest(classes = JhipsterApp.class)
public class MusicaResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_TRACK = 1;
    private static final Integer UPDATED_TRACK = 2;

    @Autowired
    private MusicaRepository musicaRepository;

    @Autowired
    private MusicaService musicaService;

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

    private MockMvc restMusicaMockMvc;

    private Musica musica;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MusicaResource musicaResource = new MusicaResource(musicaService);
        this.restMusicaMockMvc = MockMvcBuilders.standaloneSetup(musicaResource)
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
    public static Musica createEntity(EntityManager em) {
        Musica musica = new Musica()
            .name(DEFAULT_NAME)
            .track(DEFAULT_TRACK);
        return musica;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Musica createUpdatedEntity(EntityManager em) {
        Musica musica = new Musica()
            .name(UPDATED_NAME)
            .track(UPDATED_TRACK);
        return musica;
    }

    @BeforeEach
    public void initTest() {
        musica = createEntity(em);
    }

    @Test
    @Transactional
    public void createMusica() throws Exception {
        int databaseSizeBeforeCreate = musicaRepository.findAll().size();

        // Create the Musica
        restMusicaMockMvc.perform(post("/api/musicas")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(musica)))
            .andExpect(status().isCreated());

        // Validate the Musica in the database
        List<Musica> musicaList = musicaRepository.findAll();
        assertThat(musicaList).hasSize(databaseSizeBeforeCreate + 1);
        Musica testMusica = musicaList.get(musicaList.size() - 1);
        assertThat(testMusica.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMusica.getTrack()).isEqualTo(DEFAULT_TRACK);
    }

    @Test
    @Transactional
    public void createMusicaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = musicaRepository.findAll().size();

        // Create the Musica with an existing ID
        musica.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMusicaMockMvc.perform(post("/api/musicas")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(musica)))
            .andExpect(status().isBadRequest());

        // Validate the Musica in the database
        List<Musica> musicaList = musicaRepository.findAll();
        assertThat(musicaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMusicas() throws Exception {
        // Initialize the database
        musicaRepository.saveAndFlush(musica);

        // Get all the musicaList
        restMusicaMockMvc.perform(get("/api/musicas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(musica.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].track").value(hasItem(DEFAULT_TRACK)));
    }
    
    @Test
    @Transactional
    public void getMusica() throws Exception {
        // Initialize the database
        musicaRepository.saveAndFlush(musica);

        // Get the musica
        restMusicaMockMvc.perform(get("/api/musicas/{id}", musica.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(musica.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.track").value(DEFAULT_TRACK));
    }

    @Test
    @Transactional
    public void getNonExistingMusica() throws Exception {
        // Get the musica
        restMusicaMockMvc.perform(get("/api/musicas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMusica() throws Exception {
        // Initialize the database
        musicaService.save(musica);

        int databaseSizeBeforeUpdate = musicaRepository.findAll().size();

        // Update the musica
        Musica updatedMusica = musicaRepository.findById(musica.getId()).get();
        // Disconnect from session so that the updates on updatedMusica are not directly saved in db
        em.detach(updatedMusica);
        updatedMusica
            .name(UPDATED_NAME)
            .track(UPDATED_TRACK);

        restMusicaMockMvc.perform(put("/api/musicas")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedMusica)))
            .andExpect(status().isOk());

        // Validate the Musica in the database
        List<Musica> musicaList = musicaRepository.findAll();
        assertThat(musicaList).hasSize(databaseSizeBeforeUpdate);
        Musica testMusica = musicaList.get(musicaList.size() - 1);
        assertThat(testMusica.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMusica.getTrack()).isEqualTo(UPDATED_TRACK);
    }

    @Test
    @Transactional
    public void updateNonExistingMusica() throws Exception {
        int databaseSizeBeforeUpdate = musicaRepository.findAll().size();

        // Create the Musica

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMusicaMockMvc.perform(put("/api/musicas")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(musica)))
            .andExpect(status().isBadRequest());

        // Validate the Musica in the database
        List<Musica> musicaList = musicaRepository.findAll();
        assertThat(musicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMusica() throws Exception {
        // Initialize the database
        musicaService.save(musica);

        int databaseSizeBeforeDelete = musicaRepository.findAll().size();

        // Delete the musica
        restMusicaMockMvc.perform(delete("/api/musicas/{id}", musica.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Musica> musicaList = musicaRepository.findAll();
        assertThat(musicaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
