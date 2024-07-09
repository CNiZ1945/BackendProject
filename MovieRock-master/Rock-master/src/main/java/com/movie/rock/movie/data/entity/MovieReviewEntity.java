package com.movie.rock.movie.data.entity;

import com.movie.rock.member.data.MemberEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "reviews")
public class MovieReviewEntity {
    //평균별점, 총 댓글 갯수, 작성자, 내용, 평점, 작성 날짜
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long reviewId;

    @Column(name = "review_content")
    private String reviewContent;

    @Column(name = "review_rating")
        private double reviewRating;

    @Column(name = "review_date")
    private Date reviewDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mem_num", referencedColumnName = "mem_num")
    private MemberEntity member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id", referencedColumnName = "movie_id")
    private MovieEntity movie;

    @Builder
    public MovieReviewEntity(Long reviewId, String reviewContent, double reviewRating, Date reviewDate, MemberEntity member, MovieEntity movie) {
        this.reviewId = reviewId;
        this.reviewContent = reviewContent;
        this.reviewRating = reviewRating;
        this.reviewDate = reviewDate;
        this.member = member;
        this.movie = movie;
    }

    // equals, hashCode 메서드를 재정의
    // 객체 비교, 저장
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieReviewEntity that = (MovieReviewEntity) o;
        return reviewId == that.reviewId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(reviewId);
    }
}
