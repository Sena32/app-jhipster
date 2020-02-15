package com.sena.app.repository;

import com.sena.app.domain.Playlist;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Playlist entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Long> {

}
