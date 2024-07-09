package com.movie.rock.movie.data.repository;

import com.movie.rock.movie.data.entity.MovieActorsEntity;
import com.movie.rock.movie.data.pk.MovieActorsPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieActorsRepository extends JpaRepository<MovieActorsEntity, MovieActorsPK> {
    List<MovieActorsEntity> findByMovieMovieIdIn(Long movieId);
}
