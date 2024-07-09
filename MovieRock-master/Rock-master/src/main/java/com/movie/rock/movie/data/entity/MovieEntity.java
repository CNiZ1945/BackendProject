package com.movie.rock.movie.data.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "movies")
public class MovieEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_id")
    private Long movieId;

    @Column(name = "movie_name")
    private String movieTitle;

    @Column(name = "run_time")
    private int runTime;

    @Column(name = "open_year")
    private Integer openYear;

    @Column(name = "movie_rating")
    private String movieRating;

    @Column(name = "movie_description")
    private String movieDescription;

    //양방향 연결
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MovieActorsEntity> movieActors;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MovieDirectorsEntity> movieDirectors;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MoviePostersEntity> poster;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MovieTrailersEntity> trailer;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MovieReviewEntity> reviews;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MovieGenresEntity> genres;

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    private MovieFilmEntity movieFilm;

    @Builder
    public MovieEntity(Long movieId, String movieTitle, int runTime, Integer openYear, String movieRating, String movieDescription,
                       List<MovieActorsEntity> movieActors, List<MovieDirectorsEntity> movieDirectors, List<MovieGenresEntity> genres,
                       List<MoviePostersEntity> poster, List<MovieTrailersEntity> trailer, List<MovieReviewEntity> reviews,MovieFilmEntity movieFilm) {
        this.movieId = movieId;
        this.movieTitle = movieTitle;
        this.runTime = runTime;
        this.openYear = openYear;
        this.movieRating = movieRating;
        this.movieDescription = movieDescription;
        this.movieActors = movieActors;
        this.movieDirectors = movieDirectors;
        this.genres = genres;
        this.poster = poster;
        this.trailer = trailer;
        this.reviews = reviews;
        this.movieFilm = movieFilm;
    }

    //추가, 수정, 삭제 메소드
    public void setMovieActors(List<MovieActorsEntity> newMovieActors) {
        if (this.movieActors == null) {
            this.movieActors = new ArrayList<>();
        }

        // 기존 관계 제거
        this.movieActors.forEach(movieActor -> movieActor.setMovie(null));
        this.movieActors.clear();

        // 새로운 관계 설정
        if (newMovieActors != null) {
            for (MovieActorsEntity movieActor : newMovieActors) {
                movieActor.setMovie(this);
                this.movieActors.add(movieActor);
            }
        }
    }

    public void setMovieDirectors(List<MovieDirectorsEntity> newMovieDirectors) {
        if (this.movieDirectors == null) {
            this.movieDirectors = new ArrayList<>();
        }

        // 기존 관계 제거
        this.movieDirectors.forEach(movieDirector -> movieDirector.setMovie(null));
        this.movieDirectors.clear();

        // 새로운 관계 설정
        if (newMovieDirectors != null) {
            for (MovieDirectorsEntity movieDirector : newMovieDirectors) {
                movieDirector.setMovie(this);
                this.movieDirectors.add(movieDirector);
            }
        }
    }

    // 단일 배우 추가 메서드
    public void addMovieActor(MovieActorsEntity movieActor) {
        if (this.movieActors == null) {
            this.movieActors = new ArrayList<>();
        }
        movieActor.setMovie(this);
        this.movieActors.add(movieActor);
    }

    // 단일 감독 추가 메서드
    public void addMovieDirector(MovieDirectorsEntity movieDirector) {
        if (this.movieDirectors == null) {
            this.movieDirectors = new ArrayList<>();
        }
        movieDirector.setMovie(this);
        this.movieDirectors.add(movieDirector);
    }

    // 단일 배우 제거 메서드
    public void removeMovieActor(MovieActorsEntity movieActor) {
        if (this.movieActors != null) {
            movieActor.setMovie(null);
            this.movieActors.remove(movieActor);
        }
    }

    // 단일 감독 제거 메서드
    public void removeMovieDirector(MovieDirectorsEntity movieDirector) {
        if (this.movieDirectors != null) {
            movieDirector.setMovie(null);
            this.movieDirectors.remove(movieDirector);
        }
    }

    public void setPoster(MoviePostersEntity poster) {
        this.poster = new ArrayList<>();
        this.poster.add(poster);
        poster.setMovie(this);
    }

    public void setTrailer(MovieTrailersEntity trailer) {
        this.trailer = new ArrayList<>();
        this.trailer.add(trailer);
        trailer.setMovie(this);
    }

    public void setMovieFilm(MovieFilmEntity movieFilm) {
        this.movieFilm = movieFilm;
        if (movieFilm != null) {
            movieFilm.setMovie(this);
        }
    }
    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public void setMovieGenre(String genre) {
        this.genres = genres;
    }

    public void setRunTime(int runTime) {
        this.runTime = runTime;
    }

    public void setOpenYear(Integer openYear) {
        this.openYear = openYear;
    }

    public void setMovieRating(String movieRating) {
        this.movieRating = movieRating;
    }

    public void setMovieDescription(String movieDescription) {
        this.movieDescription = movieDescription;
    }


    public void addTrailer(MovieTrailersEntity trailer) {
    }

    public void addPoster(MoviePostersEntity poster) {
    }

    public String getMovieGenre() {
        if (this.genres == null || this.genres.isEmpty()) {
            return "";
        } else {
            return this.genres.stream()
                    .map(MovieGenresEntity::getGenre)
                    .map(GenresEntity::getGenreName)
                    .collect(Collectors.joining(", "));
        }
    }
}
