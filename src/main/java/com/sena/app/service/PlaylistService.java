package com.sena.app.service;

import com.sena.app.domain.Playlist;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Playlist}.
 */
public interface PlaylistService {

    /**
     * Save a playlist.
     *
     * @param playlist the entity to save.
     * @return the persisted entity.
     */
    Playlist save(Playlist playlist);

    /**
     * Get all the playlists.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Playlist> findAll(Pageable pageable);

    /**
     * Get the "id" playlist.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Playlist> findOne(Long id);

    /**
     * Delete the "id" playlist.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
