package com.movie.rock.movie.data.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "movie_posters")
public class MoviePostersEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "poster_id")
    private Long posterId;

    @Column(name = "movie_poster")
    private String posterUrls;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id", referencedColumnName = "movie_id")
    private MovieEntity movie;

    @Builder
    public MoviePostersEntity(Long posterId, String posterUrls, MovieEntity movie) {
        this.posterId = posterId;
        this.posterUrls = posterUrls;
        this.movie = movie;
    }
}
