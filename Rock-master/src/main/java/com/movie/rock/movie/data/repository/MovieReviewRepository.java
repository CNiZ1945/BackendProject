package com.movie.rock.movie.data.repository;

import com.movie.rock.movie.data.entity.MovieReviewEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieReviewRepository extends JpaRepository<MovieReviewEntity, Long> {
    //페이징, 리스트 맨 위에 본인 것 나오게 하기
    // 특정 영화에 대한 모든 리뷰 조회
    List<MovieReviewEntity> findByMovieMovieId(Long movieId);
    boolean existsByMemNumAndMovie_MovieId(Long memNum, Long movieId);
    Page<MovieReviewEntity> findByMovie_MovieId(Long movieId, Pageable pageable);

    //평균 평점 계산
    @Query("SELECT AVG(r.reviewRating) FROM MovieReviewEntity r WHERE r.movie.movieId = :movieId")
    Double getAverageRatingForMovie(@Param("movieId") Long movieId);

    //총 댓글 갯수 계산
    @Query("SELECT COUNT(r) FROM MovieReviewEntity r WHERE r.movie.movieId = :movieId")
    int countByMovie(@Param("movieId") Long movieId);

    //    맨 위에 본인 아이디에 맞는 것 올릴 때 참고
    @Query(value = "SELECT c FROM MovieReviewEntity c JOIN FETCH c.member m JOIN FETCH c.board b WHERE b.id = :reviewId")
    Optional<MovieReviewEntity> findByIdWithMemberAndBoard(Long commentId);
}
