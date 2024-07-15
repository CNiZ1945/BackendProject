package com.movie.rock.admin.data;


import com.movie.rock.movie.data.entity.ActorsEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminMovieListRepository {
    //
//    @Query(value = "SELECT m FROM MovieActorsEntity m  WHERE m.movie = :movieId AND m.actor = :actorId")
//    Optional<ActorsEntity> findByMovieIdAndActorId(Long actorId, Long movieId);
}
