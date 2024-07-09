package com.movie.rock.movie.data.response;

import com.movie.rock.movie.data.entity.ActorsPhotosEntity;
import com.movie.rock.movie.data.entity.DirectorsPhotosEntity;
import com.movie.rock.movie.data.entity.MovieEntity;
import com.movie.rock.movie.data.response.MovieInfoResponseDTO.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.movie.rock.movie.data.response.MovieInfoResponseDTO.*;

@Getter
@Setter
@NoArgsConstructor
public class MovieResponseDTO {

    private Long movieId;
    private String movieTitle;
    private List<GenreResponseDTO> genres;
    private int runTime;
    private Integer openYear;
    private String movieRating;
    private String movieDescription;
    private List<ActorResponseDTO> actors;
    private Map<Integer, String> actorsPhotos;
    private List<DirectorResponseDTO> directors;
    private Map<Integer, String> directorsPhoto;
    private List<PosterResponseDTO> poster;
    private List<TrailerResponseDTO> trailer;
    private FilmResponseDTO movieFilm;
    private List<MovieReviewResponseDTO> reviews;

    @Builder
    public MovieResponseDTO(Long movieId, String movieTitle, int runTime, Integer openYear, String movieRating, String movieDescription,
                            List<ActorResponseDTO> actors, Map<Integer, String> actorsPhotos,
                            List<DirectorResponseDTO> directors, Map<Integer, String> directorsPhoto,
                            List<GenreResponseDTO> genres, List<PosterResponseDTO> poster,
                            List<TrailerResponseDTO> trailer, FilmResponseDTO movieFilm,
                            List<MovieReviewResponseDTO> reviews) {
        this.movieId = movieId;
        this.movieTitle = movieTitle;
        this.genres = genres;
        this.runTime = runTime;
        this.openYear = openYear;
        this.movieRating = movieRating;
        this.movieDescription = movieDescription;
        this.actors = actors;
        this.actorsPhotos = actorsPhotos;
        this.directors = directors;
        this.directorsPhoto = directorsPhoto;
        this.poster = poster;
        this.trailer = trailer;
        this.movieFilm = movieFilm;
        this.reviews = reviews;
    }

    public static MovieResponseDTO fromEntity(MovieEntity movie) {
        return MovieResponseDTO.builder()
                .movieId(movie.getMovieId())
                .movieTitle(movie.getMovieTitle())
                .genres(movie.getMovieGenres().stream()
                        .map(mg -> GenreResponseDTO.fromEntity(mg.getGenre()))
                        .collect(Collectors.toList()))
                .runTime(movie.getRunTime())
                .openYear(movie.getOpenYear())
                .movieRating(movie.getMovieRating())
                .movieDescription(movie.getMovieDescription())
                .actors(movie.getMovieActors().stream()
                        .map(ma -> ActorResponseDTO.fromEntity(ma.getActor()))
                        .collect(Collectors.toList()))
                .actorsPhotos(movie.getMovieActors().stream()
                        .flatMap(ma -> ma.getActor().getActorPhotos().stream())
                        .collect(Collectors.toMap(
                                ap -> Math.toIntExact(ap.getActor().getActorId()),
                                ActorsPhotosEntity::getActorPhoto)))
                .directors(movie.getMovieDirectors().stream()
                        .map(md -> DirectorResponseDTO.fromEntity(md.getDirector()))
                        .collect(Collectors.toList()))
                .directorsPhoto(movie.getMovieDirectors().stream()
                        .flatMap(md -> md.getDirector().getDirectorPhotos().stream())
                        .collect(Collectors.toMap(
                                dp -> Math.toIntExact(dp.getDirector().getDirectorId()),
                                DirectorsPhotosEntity::getDirectorPhoto)))
                .poster(movie.getPoster().stream().map(PosterResponseDTO::fromEntity).collect(Collectors.toList()))
                .trailer(movie.getTrailer().stream().map(TrailerResponseDTO::fromEntity).collect(Collectors.toList()))
                .movieFilm(FilmResponseDTO.fromEntity(movie.getMovieFilm()))
                .reviews(movie.getReviews().stream().map(MovieReviewResponseDTO::fromEntity).collect(Collectors.toList()))
                .build();
    }
}