package com.sena.app.service;

import com.sena.app.domain.Musica;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Musica}.
 */
public interface MusicaService {

    /**
     * Save a musica.
     *
     * @param musica the entity to save.
     * @return the persisted entity.
     */
    Musica save(Musica musica);

    /**
     * Get all the musicas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Musica> findAll(Pageable pageable);

    /**
     * Get the "id" musica.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Musica> findOne(Long id);

    /**
     * Delete the "id" musica.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
