package com.sena.app.service.impl;

import com.sena.app.service.MusicaService;
import com.sena.app.domain.Musica;
import com.sena.app.repository.MusicaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Musica}.
 */
@Service
@Transactional
public class MusicaServiceImpl implements MusicaService {

    private final Logger log = LoggerFactory.getLogger(MusicaServiceImpl.class);

    private final MusicaRepository musicaRepository;

    public MusicaServiceImpl(MusicaRepository musicaRepository) {
        this.musicaRepository = musicaRepository;
    }

    /**
     * Save a musica.
     *
     * @param musica the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Musica save(Musica musica) {
        log.debug("Request to save Musica : {}", musica);
        return musicaRepository.save(musica);
    }

    /**
     * Get all the musicas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Musica> findAll(Pageable pageable) {
        log.debug("Request to get all Musicas");
        return musicaRepository.findAll(pageable);
    }

    /**
     * Get one musica by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Musica> findOne(Long id) {
        log.debug("Request to get Musica : {}", id);
        return musicaRepository.findById(id);
    }

    /**
     * Delete the musica by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Musica : {}", id);
        musicaRepository.deleteById(id);
    }
}
