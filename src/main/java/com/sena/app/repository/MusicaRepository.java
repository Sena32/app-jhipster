package com.sena.app.repository;

import com.sena.app.domain.Musica;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Musica entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MusicaRepository extends JpaRepository<Musica, Long> {

}
