package com.movie.rock.movie.data.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "trailers")
public class TrailersEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trailer_id")
    private Long trailerId;

    @Column(name = "trailer_url")
    private String trailerUrls;

    @ManyToOne
    @JoinColumn(name = "movie_id", referencedColumnName = "movie_id")
    private MovieEntity movie;

    @Builder
    public TrailersEntity(Long trailerId, String trailerUrls, MovieEntity movie) {
        this.trailerId = trailerId;
        this.trailerUrls = trailerUrls;
        this.movie = movie;
    }
}
