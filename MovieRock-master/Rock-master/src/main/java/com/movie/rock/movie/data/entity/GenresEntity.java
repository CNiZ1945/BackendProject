package com.movie.rock.movie.data.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "genres")
public class GenresEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "genre_id")
    private int genreId;

    @Column(name = "genre_name")
    private String genreName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "genre", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MovieGenresEntity> movieGenres;

    @Builder
    public GenresEntity(int genreId, String genreName, List<MovieGenresEntity> movieGenres) {
        this.genreId = genreId;
        this.genreName = genreName;
        this.movieGenres = movieGenres;
    }


    public GenresEntity(String genreName) {
        this.genreName = genreName;
    }
}
