package com.movie.rock.movie.data.repository;

import com.movie.rock.movie.data.entity.MovieActorsEntity;
import com.movie.rock.movie.data.pk.MovieActorsPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface MovieActorsRepository extends JpaRepository<MovieActorsEntity, Long> {
    // 단일 movieId로 검색하는 경우
//    List<MovieActorsEntity> findByMovieMovieId(Long movieId);

    // 여러 movieId로 검색하는 경우 (In 연산자 사용)
//    List<MovieActorsEntity> findByMovieMovieIdIn(Collection<Long> movieIds);
}
