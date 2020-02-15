package com.sena.app.service;

import com.sena.app.service.dto.FestaDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.sena.app.domain.Festa}.
 */
public interface FestaService {

    /**
     * Save a festa.
     *
     * @param festaDTO the entity to save.
     * @return the persisted entity.
     */
    FestaDTO save(FestaDTO festaDTO);

    /**
     * Get all the festas.
     *
     * @return the list of entities.
     */
    List<FestaDTO> findAll();

    /**
     * Get the "id" festa.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FestaDTO> findOne(Long id);

    /**
     * Delete the "id" festa.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
