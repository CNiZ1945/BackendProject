package com.movie.rock.movie.data.repository;

import com.movie.rock.movie.data.entity.ActorsPhotosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActorsPhotosRepository extends JpaRepository<ActorsPhotosEntity, Long> {
    List<ActorsPhotosEntity> findByActorActorsId(List<Long> actorIds);
}
