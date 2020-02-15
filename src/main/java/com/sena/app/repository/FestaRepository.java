package com.sena.app.repository;

import com.sena.app.domain.Festa;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Festa entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FestaRepository extends JpaRepository<Festa, Long> {

}
