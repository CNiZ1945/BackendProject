package com.movie.rock.movie.data.repository;

import com.movie.rock.movie.data.entity.MovieGenresEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieGenresRepository extends JpaRepository<MovieGenresEntity, Long> {
    List<MovieGenresEntity> findByMovieMovieId(Long movieId);
}
