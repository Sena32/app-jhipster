package com.sena.app.web.rest;

import com.sena.app.service.FestaService;
import com.sena.app.web.rest.errors.BadRequestAlertException;
import com.sena.app.service.dto.FestaDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.sena.app.domain.Festa}.
 */
@RestController
@RequestMapping("/api")
public class FestaResource {

    private final Logger log = LoggerFactory.getLogger(FestaResource.class);

    private final FestaService festaService;

    public FestaResource(FestaService festaService) {
        this.festaService = festaService;
    }

    /**
     * {@code GET  /festas} : get all the festas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of festas in body.
     */
    @GetMapping("/festas")
    public List<FestaDTO> getAllFestas() {
        log.debug("REST request to get all Festas");
        return festaService.findAll();
    }

    /**
     * {@code GET  /festas/:id} : get the "id" festa.
     *
     * @param id the id of the festaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the festaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/festas/{id}")
    public ResponseEntity<FestaDTO> getFesta(@PathVariable Long id) {
        log.debug("REST request to get Festa : {}", id);
        Optional<FestaDTO> festaDTO = festaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(festaDTO);
    }
}
