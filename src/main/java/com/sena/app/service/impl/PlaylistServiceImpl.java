package com.sena.app.service.impl;

import com.sena.app.service.PlaylistService;
import com.sena.app.domain.Playlist;
import com.sena.app.repository.PlaylistRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Playlist}.
 */
@Service
@Transactional
public class PlaylistServiceImpl implements PlaylistService {

    private final Logger log = LoggerFactory.getLogger(PlaylistServiceImpl.class);

    private final PlaylistRepository playlistRepository;

    public PlaylistServiceImpl(PlaylistRepository playlistRepository) {
        this.playlistRepository = playlistRepository;
    }

    /**
     * Save a playlist.
     *
     * @param playlist the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Playlist save(Playlist playlist) {
        log.debug("Request to save Playlist : {}", playlist);
        return playlistRepository.save(playlist);
    }

    /**
     * Get all the playlists.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Playlist> findAll(Pageable pageable) {
        log.debug("Request to get all Playlists");
        return playlistRepository.findAll(pageable);
    }

    /**
     * Get one playlist by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Playlist> findOne(Long id) {
        log.debug("Request to get Playlist : {}", id);
        return playlistRepository.findById(id);
    }

    /**
     * Delete the playlist by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Playlist : {}", id);
        playlistRepository.deleteById(id);
    }
}
