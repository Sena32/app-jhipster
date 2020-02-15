package com.sena.app.web.rest;

import com.sena.app.domain.Playlist;
import com.sena.app.service.PlaylistService;
import com.sena.app.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.sena.app.domain.Playlist}.
 */
@RestController
@RequestMapping("/api")
public class PlaylistResource {

    private final Logger log = LoggerFactory.getLogger(PlaylistResource.class);

    private static final String ENTITY_NAME = "playlist";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PlaylistService playlistService;

    public PlaylistResource(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    /**
     * {@code POST  /playlists} : Create a new playlist.
     *
     * @param playlist the playlist to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new playlist, or with status {@code 400 (Bad Request)} if the playlist has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/playlists")
    public ResponseEntity<Playlist> createPlaylist(@RequestBody Playlist playlist) throws URISyntaxException {
        log.debug("REST request to save Playlist : {}", playlist);
        if (playlist.getId() != null) {
            throw new BadRequestAlertException("A new playlist cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Playlist result = playlistService.save(playlist);
        return ResponseEntity.created(new URI("/api/playlists/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /playlists} : Updates an existing playlist.
     *
     * @param playlist the playlist to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated playlist,
     * or with status {@code 400 (Bad Request)} if the playlist is not valid,
     * or with status {@code 500 (Internal Server Error)} if the playlist couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/playlists")
    public ResponseEntity<Playlist> updatePlaylist(@RequestBody Playlist playlist) throws URISyntaxException {
        log.debug("REST request to update Playlist : {}", playlist);
        if (playlist.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Playlist result = playlistService.save(playlist);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, playlist.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /playlists} : get all the playlists.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of playlists in body.
     */
    @GetMapping("/playlists")
    public ResponseEntity<List<Playlist>> getAllPlaylists(Pageable pageable) {
        log.debug("REST request to get a page of Playlists");
        Page<Playlist> page = playlistService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /playlists/:id} : get the "id" playlist.
     *
     * @param id the id of the playlist to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the playlist, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/playlists/{id}")
    public ResponseEntity<Playlist> getPlaylist(@PathVariable Long id) {
        log.debug("REST request to get Playlist : {}", id);
        Optional<Playlist> playlist = playlistService.findOne(id);
        return ResponseUtil.wrapOrNotFound(playlist);
    }

    /**
     * {@code DELETE  /playlists/:id} : delete the "id" playlist.
     *
     * @param id the id of the playlist to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/playlists/{id}")
    public ResponseEntity<Void> deletePlaylist(@PathVariable Long id) {
        log.debug("REST request to delete Playlist : {}", id);
        playlistService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
