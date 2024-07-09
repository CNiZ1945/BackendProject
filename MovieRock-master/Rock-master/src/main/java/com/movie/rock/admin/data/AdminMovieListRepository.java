package com.movie.rock.admin.data;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminMovieListRepository extends JpaRepository<AdminMovieListEntity,Long> {

}
