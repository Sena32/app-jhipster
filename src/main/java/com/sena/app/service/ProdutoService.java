package com.sena.app.service;

import com.sena.app.service.dto.ProdutoDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.sena.app.domain.Produto}.
 */
public interface ProdutoService {

    /**
     * Save a produto.
     *
     * @param produtoDTO the entity to save.
     * @return the persisted entity.
     */
    ProdutoDTO save(ProdutoDTO produtoDTO);

    /**
     * Get all the produtos.
     *
     * @return the list of entities.
     */
    List<ProdutoDTO> findAll();

    /**
     * Get the "id" produto.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProdutoDTO> findOne(Long id);

    /**
     * Delete the "id" produto.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
