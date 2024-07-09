package com.movie.rock.movie.data.repository;

import com.movie.rock.movie.data.entity.MovieFavorEntity;
import com.movie.rock.movie.data.pk.MovieFavorPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieFavorRepository extends JpaRepository<MovieFavorEntity, MovieFavorPK> {
    List<MovieFavorEntity> findByMovieMovieId(Long movieId);
}
