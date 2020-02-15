package com.sena.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

import java.io.Serializable;

/**
 * The Employee entity.
 */
@ApiModel(description = "The Employee entity.")
@Entity
@Table(name = "musica")
public class Musica implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * The firstname attribute.
     */
    @ApiModelProperty(value = "The firstname attribute.")
    @Column(name = "name")
    private String name;

    @Column(name = "track")
    private Integer track;

    @ManyToOne
    @JsonIgnoreProperties("musicas")
    private Playlist playlist;

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

    public Musica name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTrack() {
        return track;
    }

    public Musica track(Integer track) {
        this.track = track;
        return this;
    }

    public void setTrack(Integer track) {
        this.track = track;
    }

    public Playlist getPlaylist() {
        return playlist;
    }

    public Musica playlist(Playlist playlist) {
        this.playlist = playlist;
        return this;
    }

    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Musica)) {
            return false;
        }
        return id != null && id.equals(((Musica) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Musica{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", track=" + getTrack() +
            "}";
    }
}
