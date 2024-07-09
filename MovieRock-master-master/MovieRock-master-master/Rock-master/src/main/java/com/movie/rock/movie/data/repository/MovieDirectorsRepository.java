package com.movie.rock.movie.data.repository;

import com.movie.rock.movie.data.entity.MovieDirectorsEntity;
import com.movie.rock.movie.data.pk.MovieDirectorsPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieDirectorsRepository extends JpaRepository<MovieDirectorsEntity, MovieDirectorsPK> {
    List<MovieDirectorsEntity> findByMovieMovieId(Long movieId);
}
