package com.sena.app.web.rest;

import com.sena.app.domain.Musica;
import com.sena.app.service.MusicaService;
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
 * REST controller for managing {@link com.sena.app.domain.Musica}.
 */
@RestController
@RequestMapping("/api")
public class MusicaResource {

    private final Logger log = LoggerFactory.getLogger(MusicaResource.class);

    private static final String ENTITY_NAME = "musica";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MusicaService musicaService;

    public MusicaResource(MusicaService musicaService) {
        this.musicaService = musicaService;
    }

    /**
     * {@code POST  /musicas} : Create a new musica.
     *
     * @param musica the musica to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new musica, or with status {@code 400 (Bad Request)} if the musica has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/musicas")
    public ResponseEntity<Musica> createMusica(@RequestBody Musica musica) throws URISyntaxException {
        log.debug("REST request to save Musica : {}", musica);
        if (musica.getId() != null) {
            throw new BadRequestAlertException("A new musica cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Musica result = musicaService.save(musica);
        return ResponseEntity.created(new URI("/api/musicas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /musicas} : Updates an existing musica.
     *
     * @param musica the musica to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated musica,
     * or with status {@code 400 (Bad Request)} if the musica is not valid,
     * or with status {@code 500 (Internal Server Error)} if the musica couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/musicas")
    public ResponseEntity<Musica> updateMusica(@RequestBody Musica musica) throws URISyntaxException {
        log.debug("REST request to update Musica : {}", musica);
        if (musica.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Musica result = musicaService.save(musica);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, musica.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /musicas} : get all the musicas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of musicas in body.
     */
    @GetMapping("/musicas")
    public ResponseEntity<List<Musica>> getAllMusicas(Pageable pageable) {
        log.debug("REST request to get a page of Musicas");
        Page<Musica> page = musicaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /musicas/:id} : get the "id" musica.
     *
     * @param id the id of the musica to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the musica, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/musicas/{id}")
    public ResponseEntity<Musica> getMusica(@PathVariable Long id) {
        log.debug("REST request to get Musica : {}", id);
        Optional<Musica> musica = musicaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(musica);
    }

    /**
     * {@code DELETE  /musicas/:id} : delete the "id" musica.
     *
     * @param id the id of the musica to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/musicas/{id}")
    public ResponseEntity<Void> deleteMusica(@PathVariable Long id) {
        log.debug("REST request to delete Musica : {}", id);
        musicaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
