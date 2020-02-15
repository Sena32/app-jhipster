package com.sena.app.service.impl;

import com.sena.app.service.FestaService;
import com.sena.app.domain.Festa;
import com.sena.app.repository.FestaRepository;
import com.sena.app.service.dto.FestaDTO;
import com.sena.app.service.mapper.FestaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Festa}.
 */
@Service
@Transactional
public class FestaServiceImpl implements FestaService {

    private final Logger log = LoggerFactory.getLogger(FestaServiceImpl.class);

    private final FestaRepository festaRepository;

    private final FestaMapper festaMapper;

    public FestaServiceImpl(FestaRepository festaRepository, FestaMapper festaMapper) {
        this.festaRepository = festaRepository;
        this.festaMapper = festaMapper;
    }

    /**
     * Save a festa.
     *
     * @param festaDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public FestaDTO save(FestaDTO festaDTO) {
        log.debug("Request to save Festa : {}", festaDTO);
        Festa festa = festaMapper.toEntity(festaDTO);
        festa = festaRepository.save(festa);
        return festaMapper.toDto(festa);
    }

    /**
     * Get all the festas.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<FestaDTO> findAll() {
        log.debug("Request to get all Festas");
        return festaRepository.findAll().stream()
            .map(festaMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one festa by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<FestaDTO> findOne(Long id) {
        log.debug("Request to get Festa : {}", id);
        return festaRepository.findById(id)
            .map(festaMapper::toDto);
    }

    /**
     * Delete the festa by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Festa : {}", id);
        festaRepository.deleteById(id);
    }
}
