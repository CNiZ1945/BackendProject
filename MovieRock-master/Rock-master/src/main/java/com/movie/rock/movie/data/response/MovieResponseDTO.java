package com.movie.rock.movie.data.response;

import com.movie.rock.movie.data.entity.MovieEntity;
import com.movie.rock.movie.data.response.MovieInfoResponseDTO.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

import static com.movie.rock.movie.data.response.MovieInfoResponseDTO.*;

@Getter
@Setter
@NoArgsConstructor
public class MovieResponseDTO {

    //영화정보
    private Long movieId;
    private String movieTitle;
    private String movieGenre;
    private int runTime;
    private Integer openYear;
    private String movieRating;
    private String movieDescription;

    //출연배우
    private List<ActorResponseDTO> movieActors;

    //감독
    private List<DirectorResponseDTO> movieDirectors;

    //포스터
    private List<PosterResponseDTO> poster;

    //예고편
    private List<TrailerResponseDTO> trailer;

    //영화 필름
    private FilmResponseDTO movieFilm;

    //리뷰
    private List<MovieReviewResponseDTO> reviews;

    @Builder
    public MovieResponseDTO(Long movieId, String movieTitle, String movieGenre, int runTime, Integer openYear, String movieRating, String movieDescription,
                            List<ActorResponseDTO> movieActors, List<DirectorResponseDTO> movieDirectors,
                            List<PosterResponseDTO> poster, List<TrailerResponseDTO> trailer,
                            FilmResponseDTO movieFilm, List<MovieReviewResponseDTO> reviews) {
        this.movieId = movieId;
        this.movieTitle = movieTitle;
        this.movieGenre = movieGenre;
        this.runTime = runTime;
        this.openYear = openYear;
        this.movieRating = movieRating;
        this.movieDescription = movieDescription;
        this.movieActors = movieActors;
        this.movieDirectors = movieDirectors;
        this.poster = poster;
        this.trailer = trailer;
        this.movieFilm = movieFilm;
        this.reviews = reviews;
    }

    //DTO
    public static MovieResponseDTO fromEntity(MovieEntity movie) {
        return MovieResponseDTO.builder()
                .movieId(movie.getMovieId())
                .movieTitle(movie.getMovieTitle())
                .movieGenre(movie.getMovieGenre())
                .runTime(movie.getRunTime())
                .openYear(movie.getOpenYear())
                .movieRating(movie.getMovieRating())
                .movieDescription(movie.getMovieDescription())
                .movieActors(movie.getMovieActors().stream()
                        .map(ma -> ActorResponseDTO.fromEntity(ma.getActor()))
                        .collect(Collectors.toList()))
                .movieDirectors(movie.getMovieDirectors().stream()
                        .map(md -> DirectorResponseDTO.fromEntity(md.getDirector()))
                        .collect(Collectors.toList()))
                .poster(movie.getPoster().stream().map(PosterResponseDTO::fromEntity).collect(Collectors.toList()))
                .trailer(movie.getTrailer().stream().map(TrailerResponseDTO::fromEntity).collect(Collectors.toList()))
                .movieFilm(FilmResponseDTO.fromEntity(movie.getMovieFilm()))
                .reviews(movie.getReviews().stream().map(MovieReviewResponseDTO::fromEntity).collect(Collectors.toList()))
                .build();
    }
}
