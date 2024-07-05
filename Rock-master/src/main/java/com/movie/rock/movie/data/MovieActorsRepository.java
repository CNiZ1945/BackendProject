package com.movie.rock.movie.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieActorsRepository extends JpaRepository<MovieActorsEntity, MovieActorsPK> {
}
