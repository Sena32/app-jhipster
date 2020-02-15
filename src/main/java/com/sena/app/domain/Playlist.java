package com.sena.app.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A Playlist.
 */
@Entity
@Table(name = "playlist")
public class Playlist implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "style")
    private String style;

    @Column(name = "dt_create")
    private Instant dtCreate;

    @OneToMany(mappedBy = "playlist")
    private Set<Musica> musicas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Playlist name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStyle() {
        return style;
    }

    public Playlist style(String style) {
        this.style = style;
        return this;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public Instant getDtCreate() {
        return dtCreate;
    }

    public Playlist dtCreate(Instant dtCreate) {
        this.dtCreate = dtCreate;
        return this;
    }

    public void setDtCreate(Instant dtCreate) {
        this.dtCreate = dtCreate;
    }

    public Set<Musica> getMusicas() {
        return musicas;
    }

    public Playlist musicas(Set<Musica> musicas) {
        this.musicas = musicas;
        return this;
    }

    public Playlist addMusica(Musica musica) {
        this.musicas.add(musica);
        musica.setPlaylist(this);
        return this;
    }

    public Playlist removeMusica(Musica musica) {
        this.musicas.remove(musica);
        musica.setPlaylist(null);
        return this;
    }

    public void setMusicas(Set<Musica> musicas) {
        this.musicas = musicas;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Playlist)) {
            return false;
        }
        return id != null && id.equals(((Playlist) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Playlist{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", style='" + getStyle() + "'" +
            ", dtCreate='" + getDtCreate() + "'" +
            "}";
    }
}
