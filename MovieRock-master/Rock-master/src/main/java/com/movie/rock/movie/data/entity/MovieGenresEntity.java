package com.movie.rock.movie.data.entity;

import com.movie.rock.movie.data.pk.MovieActorsPK;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "movie_genres")
public class MovieGenresEntity {

    @EmbeddedId
    private MovieActorsPK id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("movieId")
    @JoinColumn(name = "movie_id")
    private MovieEntity movie;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("genreId")
    @JoinColumn(name = "genre_id")
    private GenresEntity genre;

    @Builder
    public MovieGenresEntity(MovieEntity movie, GenresEntity genre) {
        this.movie = movie;
        this.genre = genre;
        this.id = new MovieActorsPK(movie.getMovieId(), genre.getGenreId());

    }

    public void setMovie(Object o) {
    }
}
