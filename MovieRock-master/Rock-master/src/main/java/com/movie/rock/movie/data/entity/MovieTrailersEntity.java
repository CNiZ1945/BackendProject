package com.movie.rock.movie.data.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "movie_trailers")
public class MovieTrailersEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trailer_id")
    private int trailerId;

    @Column(name = "movie_trailer")
    private String movieTrailer;

    @ManyToOne
    @JoinColumn(name = "movie_id", referencedColumnName = "movie_id")
    private MovieEntity movie;

    @Builder
    public MovieTrailersEntity(int trailerId, String movieTrailer, MovieEntity movie) {
        this.trailerId = trailerId;
        this.movieTrailer = movieTrailer;
        this.movie = movie;
    }

    public void setMovie(MovieEntity movie) {
        this.movie = movie;
    }
}
