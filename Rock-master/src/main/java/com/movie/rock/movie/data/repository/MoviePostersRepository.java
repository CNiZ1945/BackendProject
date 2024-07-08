package com.movie.rock.movie.data.repository;

import com.movie.rock.movie.data.entity.MoviePostersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MoviePostersRepository extends JpaRepository<MoviePostersEntity, Integer> {
    List<MoviePostersEntity> findByMovieMovieId(Long movieId);
}
