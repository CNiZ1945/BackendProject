package com.movie.rock.movie.data.response;

import com.movie.rock.movie.data.MovieReviewEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
public class  MovieReviewResponseDTO {

    private int reviewId;
    private String reviewContent;
    private double reviewRating;
    private Date reviewDate;

    @Builder
    public MovieReviewResponseDTO(int reviewId, String reviewContent, double reviewRating, Date reviewDate) {
        this.reviewId = reviewId;
        this.reviewContent = reviewContent;
        this.reviewRating = reviewRating;
        this.reviewDate = reviewDate;
    }

    public static MovieReviewResponseDTO fromEntity(MovieReviewEntity review) {
        return MovieReviewResponseDTO.builder()
                .reviewId(review.getReviewId())
                .reviewContent(review.getReviewContent())
                .reviewRating(review.getReviewRating())
                .reviewDate(review.getReviewDate())
                .build();
    }
}
