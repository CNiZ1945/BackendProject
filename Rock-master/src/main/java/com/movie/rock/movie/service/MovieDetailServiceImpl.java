package com.movie.rock.movie.service;

import com.movie.rock.movie.data.entity.*;
import com.movie.rock.movie.data.repository.*;
import com.movie.rock.movie.data.response.MovieInfoResponseDTO.*;
import com.movie.rock.movie.data.response.MovieResponseDTO;
import com.movie.rock.movie.data.response.MovieReviewResponseDTO;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@NoArgsConstructor
public class MovieDetailServiceImpl implements MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private MovieActorsRepository movieActorsRepository;

    @Autowired
    private MovieDirectorsRepository movieDirectorsRepository;

    @Autowired
    private ActorsPhotosRepository actorsPhotosRepository;

    @Autowired
    private DirectorsPhotosRepository directorsPhotosRepository;

    @Autowired
    private MovieGenresRepository movieGenresRepository;

    @Autowired
    private MoviePostersRepository moviePostersRepository;

    @Autowired
    private MovieTrailersRepository movieTrailersRepository;

    @Autowired
    private MovieFilmRepository movieFilmRepository;

    @Autowired
    private MovieReviewRepository movieReviewRepository;

    @Override
    @Transactional(readOnly = true)
    public MovieResponseDTO getMovieDetail(Long movieId) {
        Optional<MovieEntity> optionalMovie = movieRepository.findByMovieId(movieId);
        if (optionalMovie.isEmpty()) {
            throw new RuntimeException("영화를 찾을 수 없습니다.");
        }
        MovieEntity movie = optionalMovie.get();

        List<MovieActorsEntity> movieActors = movieActorsRepository.findByMovieMovieIdIn(movie.getMovieId());
        List<MovieDirectorsEntity> movieDirectors = movieDirectorsRepository.findByMovieMovieIdIn(movie.getMovieId());
        List<ActorsPhotosEntity> actorsPhotos = actorsPhotosRepository.findByActorActorsId(movieActors.stream()
                .map(MovieActorsEntity::getActor)
                .map(ActorsEntity::getActorId)
                .collect(Collectors.toList()));
        List<DirectorsPhotosEntity> directorsPhotos = directorsPhotosRepository.findByDirectorDirectorsIdIn(movieDirectors.stream()
                .map(MovieDirectorsEntity::getDirector)
                .map(DirectorsEntity::getDirectorId)
                .collect(Collectors.toList()));
        List<MovieGenresEntity> movieGenres = movieGenresRepository.findByMovieMovieId(movie.getMovieId());
        List<MoviePostersEntity> posters = moviePostersRepository.findByMovieMovieId(movie.getMovieId());
        List<MovieTrailersEntity> trailers = movieTrailersRepository.findByMovieMovieId(movie.getMovieId());
        Optional<MovieFilmEntity> film = movieFilmRepository.findByMovieMovieId(movie.getMovieId());

        // MovieResponseDTO 생성 및 데이터 채우기
        return MovieResponseDTO.builder()
                .movieId(movie.getMovieId())
                .movieTitle(movie.getMovieTitle())
                .genres(movieGenres.stream()
                        .map(mg -> GenreResponseDTO.fromEntity(mg.getGenre()))
                        .collect(Collectors.toList()))
                .runTime(movie.getRunTime())
                .openYear(movie.getOpenYear())
                .movieRating(movie.getMovieRating())
                .movieDescription(movie.getMovieDescription())
                .actors(movieActors.stream()
                        .map(ma -> ActorResponseDTO.fromEntity(ma.getActor()))
                        .collect(Collectors.toList()))
                .actorsPhotos(actorsPhotos.stream()
                        .collect(Collectors.toMap(
                                ap -> Math.toIntExact(ap.getActor().getActorId()),
                                ActorsPhotosEntity::getActorPhoto)))
                .directors(movieDirectors.stream()
                        .map(md -> DirectorResponseDTO.fromEntity(md.getDirector()))
                        .collect(Collectors.toList()))
                .directorsPhoto(directorsPhotos.stream()
                        .collect(Collectors.toMap(
                                dp -> Math.toIntExact(dp.getDirector().getDirectorId()),
                                DirectorsPhotosEntity::getDirectorPhoto)))
                .poster(posters.stream()
                        .map(PosterResponseDTO::fromEntity)
                        .collect(Collectors.toList()))
                .trailer(trailers.stream()
                        .map(TrailerResponseDTO::fromEntity)
                        .collect(Collectors.toList()))
                .movieFilm(film.map(FilmResponseDTO::fromEntity).orElse(null)) // 수정된 부분
                .reviews(movie.getReviews().stream()
                        .map(MovieReviewResponseDTO::fromEntity)
                        .collect(Collectors.toList()))
                .build();
    }
}
