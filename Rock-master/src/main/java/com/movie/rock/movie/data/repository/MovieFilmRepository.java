package com.movie.rock.movie.data.repository;

import com.movie.rock.movie.data.entity.MovieFilmEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MovieFilmRepository extends JpaRepository<MovieFilmEntity, Long> {
//    Optional<MovieFilmEntity> findByMovieMovieId(Long movieId);
}
