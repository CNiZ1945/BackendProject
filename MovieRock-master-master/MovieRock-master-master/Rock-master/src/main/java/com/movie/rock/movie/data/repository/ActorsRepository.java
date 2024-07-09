package com.movie.rock.movie.data.repository;

import com.movie.rock.movie.data.entity.ActorsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActorsRepository extends JpaRepository<ActorsEntity, Long> {
}
