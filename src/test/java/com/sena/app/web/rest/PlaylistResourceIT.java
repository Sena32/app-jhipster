package com.sena.app.web.rest;

import com.sena.app.JhipsterApp;
import com.sena.app.domain.Playlist;
import com.sena.app.repository.PlaylistRepository;
import com.sena.app.service.PlaylistService;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.sena.app.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PlaylistResource} REST controller.
 */
@SpringBootTest(classes = JhipsterApp.class)
public class PlaylistResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_STYLE = "AAAAAAAAAA";
    private static final String UPDATED_STYLE = "BBBBBBBBBB";

    private static final Instant DEFAULT_DT_CREATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DT_CREATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private PlaylistRepository playlistRepository;

    @Autowired
    private PlaylistService playlistService;

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

    private MockMvc restPlaylistMockMvc;

    private Playlist playlist;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PlaylistResource playlistResource = new PlaylistResource(playlistService);
        this.restPlaylistMockMvc = MockMvcBuilders.standaloneSetup(playlistResource)
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
    public static Playlist createEntity(EntityManager em) {
        Playlist playlist = new Playlist()
            .name(DEFAULT_NAME)
            .style(DEFAULT_STYLE)
            .dtCreate(DEFAULT_DT_CREATE);
        return playlist;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Playlist createUpdatedEntity(EntityManager em) {
        Playlist playlist = new Playlist()
            .name(UPDATED_NAME)
            .style(UPDATED_STYLE)
            .dtCreate(UPDATED_DT_CREATE);
        return playlist;
    }

    @BeforeEach
    public void initTest() {
        playlist = createEntity(em);
    }

    @Test
    @Transactional
    public void createPlaylist() throws Exception {
        int databaseSizeBeforeCreate = playlistRepository.findAll().size();

        // Create the Playlist
        restPlaylistMockMvc.perform(post("/api/playlists")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(playlist)))
            .andExpect(status().isCreated());

        // Validate the Playlist in the database
        List<Playlist> playlistList = playlistRepository.findAll();
        assertThat(playlistList).hasSize(databaseSizeBeforeCreate + 1);
        Playlist testPlaylist = playlistList.get(playlistList.size() - 1);
        assertThat(testPlaylist.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPlaylist.getStyle()).isEqualTo(DEFAULT_STYLE);
        assertThat(testPlaylist.getDtCreate()).isEqualTo(DEFAULT_DT_CREATE);
    }

    @Test
    @Transactional
    public void createPlaylistWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = playlistRepository.findAll().size();

        // Create the Playlist with an existing ID
        playlist.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlaylistMockMvc.perform(post("/api/playlists")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(playlist)))
            .andExpect(status().isBadRequest());

        // Validate the Playlist in the database
        List<Playlist> playlistList = playlistRepository.findAll();
        assertThat(playlistList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPlaylists() throws Exception {
        // Initialize the database
        playlistRepository.saveAndFlush(playlist);

        // Get all the playlistList
        restPlaylistMockMvc.perform(get("/api/playlists?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(playlist.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].style").value(hasItem(DEFAULT_STYLE)))
            .andExpect(jsonPath("$.[*].dtCreate").value(hasItem(DEFAULT_DT_CREATE.toString())));
    }
    
    @Test
    @Transactional
    public void getPlaylist() throws Exception {
        // Initialize the database
        playlistRepository.saveAndFlush(playlist);

        // Get the playlist
        restPlaylistMockMvc.perform(get("/api/playlists/{id}", playlist.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(playlist.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.style").value(DEFAULT_STYLE))
            .andExpect(jsonPath("$.dtCreate").value(DEFAULT_DT_CREATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPlaylist() throws Exception {
        // Get the playlist
        restPlaylistMockMvc.perform(get("/api/playlists/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePlaylist() throws Exception {
        // Initialize the database
        playlistService.save(playlist);

        int databaseSizeBeforeUpdate = playlistRepository.findAll().size();

        // Update the playlist
        Playlist updatedPlaylist = playlistRepository.findById(playlist.getId()).get();
        // Disconnect from session so that the updates on updatedPlaylist are not directly saved in db
        em.detach(updatedPlaylist);
        updatedPlaylist
            .name(UPDATED_NAME)
            .style(UPDATED_STYLE)
            .dtCreate(UPDATED_DT_CREATE);

        restPlaylistMockMvc.perform(put("/api/playlists")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPlaylist)))
            .andExpect(status().isOk());

        // Validate the Playlist in the database
        List<Playlist> playlistList = playlistRepository.findAll();
        assertThat(playlistList).hasSize(databaseSizeBeforeUpdate);
        Playlist testPlaylist = playlistList.get(playlistList.size() - 1);
        assertThat(testPlaylist.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPlaylist.getStyle()).isEqualTo(UPDATED_STYLE);
        assertThat(testPlaylist.getDtCreate()).isEqualTo(UPDATED_DT_CREATE);
    }

    @Test
    @Transactional
    public void updateNonExistingPlaylist() throws Exception {
        int databaseSizeBeforeUpdate = playlistRepository.findAll().size();

        // Create the Playlist

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlaylistMockMvc.perform(put("/api/playlists")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(playlist)))
            .andExpect(status().isBadRequest());

        // Validate the Playlist in the database
        List<Playlist> playlistList = playlistRepository.findAll();
        assertThat(playlistList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePlaylist() throws Exception {
        // Initialize the database
        playlistService.save(playlist);

        int databaseSizeBeforeDelete = playlistRepository.findAll().size();

        // Delete the playlist
        restPlaylistMockMvc.perform(delete("/api/playlists/{id}", playlist.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Playlist> playlistList = playlistRepository.findAll();
        assertThat(playlistList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
