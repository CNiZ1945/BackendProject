package com.movie.rock.movie.service;

import com.movie.rock.movie.data.entity.*;
import com.movie.rock.movie.data.repository.*;
import com.movie.rock.movie.data.request.MovieRequestDTO;
import com.movie.rock.movie.data.response.MovieResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private MovieActorsRepository movieActorsRepository;
    @Autowired
    private MovieDirectorsRepository movieDirectorsRepository;
    @Autowired
    private MoviePostersRepository moviePostersRepository;
    @Autowired
    private MovieTrailersRepository movieTrailersRepository;
    @Autowired
    private MovieFilmRepository movieFilmRepository;
    @Autowired
    private MovieGenresRepository movieGenresRepository;
    @Autowired
    private ActorsRepository actorsRepository;
    @Autowired
    private DirectorsRepository directorsRepository;
    @Autowired
    private GenresRepository genresRepository;
    @Autowired
    private MovieReviewRepository movieReviewRepository;

    // 새로운 영화를 추가합니다.
    @Override
    @Transactional
    public MovieResponseDTO addMovie(MovieRequestDTO requestDTO) {
        MovieEntity movie = createMovie(requestDTO);
        movie = movieRepository.save(movie);
        // 영화에 관련된 데이터들(배우, 감독, 장르, 포스터, 트레일러, 영화 파일)을 추가합니다.
        addMovieActors(movie, requestDTO.getActorIds());
        addMovieDirectors(movie, requestDTO.getDirectorIds());
        addMovieGenres(movie, requestDTO.getGenres());
        addMoviePosters(movie, requestDTO.getPosterUrls());
        addMovieTrailers(movie, requestDTO.getTrailerUrls());
        addMovieFilm(movie, requestDTO.getFilmUrl());

        // 추가된 영화 정보를 DTO로 변환하여 반환합니다.
        return MovieResponseDTO.fromEntity(movie);
    }

    @Override
    public MovieResponseDTO updateMovie(int movieId, MovieRequestDTO requestDTO) {
        return updateMovie((long) movieId, requestDTO);
    }


    @Override
    @Transactional
    public MovieResponseDTO updateMovie(Long movieId, MovieRequestDTO requestDTO) {
        MovieEntity movie = movieRepository.findById(movieId).orElseThrow(() -> new IllegalArgumentException("Movie not found with id: " + movieId));

        movie.setMovieTitle(requestDTO.getMovieTitle());
        movie.setMovieGenre(String.join(", ", requestDTO.getGenres()));
        movie.setRunTime(requestDTO.getRunTime());
        movie.setOpenYear(requestDTO.getOpenYear());
        movie.setMovieRating(requestDTO.getMovieRating());
        movie.setMovieDescription(requestDTO.getMovieDescription());

        updateMovieGenres(movie, requestDTO.getGenres());

        movie = movieRepository.save(movie);
        return MovieResponseDTO.fromEntity(movie);
    }

    private void updateMovieGenres(MovieEntity movie, List<String> genres) {
        // 기존 장르 관계 제거
        List<MovieGenresEntity> existingMovieGenres = movieGenresRepository.findByMovieMovieId(movie.getMovieId());
        existingMovieGenres.forEach(movieGenre -> {
            movie.getGenres().remove(movieGenre);
            movieGenresRepository.delete(movieGenre);
        });

        // 새로운 장르 관계 추가
        addMovieGenres(movie, genres);
    }

    @Override
    public void deleteMovie(int movieId) {
        deleteMovie((long) movieId);
    }

    @Override
    @Transactional
    public void deleteMovie(Long movieId) {
        // movieId로 영화 엔티티를 찾아 삭제합니다.
        MovieEntity movie = movieRepository.findById(movieId).orElseThrow(() -> new IllegalArgumentException("Movie not found with id: " + movieId));
        movieRepository.delete(movie);
    }

    // 특정 영화 정보를 조회합니다.
    @Override
    public MovieResponseDTO getMovie(int movieId) {
        return getMovie((long) movieId);
    }

    @Override
    public MovieResponseDTO getMovie(Long movieId) {
        MovieEntity movie = movieRepository.findById(movieId).orElseThrow(() -> new IllegalArgumentException("Movie not found with id: " + movieId));
        return MovieResponseDTO.fromEntity(movie);
    }

    // 모든 영화 정보를 조회합니다.
    @Override
    public List<MovieResponseDTO> getAllMovies() {
        List<MovieEntity> movies = movieRepository.findAll();
        // 영화 엔티티들을 DTO로 변환하여 반환합니다.
        return movies.stream()
                .map(MovieResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    private MovieEntity createMovie(MovieRequestDTO requestDTO) {
        // 장르 정보를 MovieGenresEntity로 변환합니다.
        List<MovieGenresEntity> genreEntities = new ArrayList<>();
        for (String genreName : requestDTO.getGenres()) {
            GenresEntity genre = genresRepository.findByGenreName(genreName)
                    .orElseGet(() -> {
                        GenresEntity newGenre = new GenresEntity(genreName);
                        return genresRepository.save(newGenre);
                    });
            MovieGenresEntity movieGenre = MovieGenresEntity.builder()
                    .movie(null) // 임시 movieEntity 값 null
                    .genre(genre)
                    .build();
            genreEntities.add(movieGenre);
        }

        // 새로운 영화 엔티티를 생성하고 반환합니다.
        return MovieEntity.builder()
                .movieTitle(requestDTO.getMovieTitle())
                .genres(genreEntities)
                .runTime(requestDTO.getRunTime())
                .openYear(requestDTO.getOpenYear())
                .movieRating(requestDTO.getMovieRating())
                .movieDescription(requestDTO.getMovieDescription())
                .build();
    }

    //영화 정보 추가
    private void addMovieActors(MovieEntity movieEntity, List<Long> actorIds) {
        if (actorIds != null) {
            for (Long actorId : actorIds) {
                ActorsEntity actor = actorsRepository.findById(actorId).orElseThrow(() -> new IllegalArgumentException("Actor not found with id: " + actorId));
                MovieActorsEntity movieActor = new MovieActorsEntity(movieEntity, actor);
                movieEntity.addMovieActor(movieActor);
                movieActorsRepository.save(movieActor);
            }
        }
    }

    //감독 정보 추가
    private void addMovieDirectors(MovieEntity movieEntity, List<Long> directorIds) {
        if (directorIds != null) {
            for (Long directorId : directorIds) {
                DirectorsEntity director = directorsRepository.findById(directorId).orElseThrow(() -> new IllegalArgumentException("Director not found with id: " + directorId));
                MovieDirectorsEntity movieDirector = new MovieDirectorsEntity(movieEntity, director);
                movieEntity.addMovieDirector(movieDirector);
                movieDirectorsRepository.save(movieDirector);
            }
        }
    }

    //장르 정보 추가
    private void addMovieGenres(MovieEntity movie, List<String> genres) {
        if (genres != null) {
            for (String genreName : genres) {
                GenresEntity genre = genresRepository.findByGenreName(genreName)
                        .orElseGet(() -> {
                            GenresEntity newGenre = new GenresEntity(genreName);
                            return genresRepository.save(newGenre);
                        });
                MovieGenresEntity movieGenre = MovieGenresEntity.builder()
                        .movie(movie)
                        .genre(genre)
                        .build();
                movie.getGenres().add(movieGenre);
                movieGenresRepository.save(movieGenre);
            }
        }
    }

    //포스터 정보 추가
    private void addMoviePosters(MovieEntity movie, List<String> posterUrls) {
        if (posterUrls != null) {
            for (String posterUrl : posterUrls) {
                MoviePostersEntity poster = MoviePostersEntity.builder()
                        .moviePoster(posterUrl)
                        .movie(movie)
                        .build();
                movie.addPoster(poster);
                moviePostersRepository.save(poster);
            }
        }
    }


    //트레일러 정보 추가
    private void addMovieTrailers(MovieEntity movie, List<String> trailerUrls) {
        if (trailerUrls != null) {
            for (String trailerUrl : trailerUrls) {
                MovieTrailersEntity trailer = MovieTrailersEntity.builder()
                        .movieTrailer(trailerUrl)
                        .movie(movie)
                        .build();
                movie.addTrailer(trailer);
                movieTrailersRepository.save(trailer);
            }
        }
    }

    //영화 파일 정보 추가
    private void addMovieFilm(MovieEntity movie, String filmUrl) {
        if (filmUrl != null) {
            MovieFilmEntity film = MovieFilmEntity.builder()
                    .movieFilm(filmUrl)
                    .movie(movie)
                    .build();
            movie.setMovieFilm(film);
            movieFilmRepository.save(film);
        }
    }
}