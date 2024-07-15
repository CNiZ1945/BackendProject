package com.movie.rock.admin.data.request;


import com.movie.rock.movie.data.entity.MovieEntity;
import com.movie.rock.movie.data.entity.MovieTrailersEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AdminTrailerRequestDTO {

    private MovieEntity movie;
    private Long trailerId;
    private String movieTrailer; //트레일러 URL

    //생성자
    private AdminTrailerRequestDTO(MovieEntity movie,Long trailerId,String movieTrailer){
        this.movie = movie;
        this.trailerId = trailerId;
        this.movieTrailer = movieTrailer;
    }

    //생성자에 넣을 데이터
    @Builder
    public static MovieTrailersEntity ofEntity(AdminTrailerRequestDTO adminTrailerRequestDTO){
        return MovieTrailersEntity.builder()
                .movie(adminTrailerRequestDTO.getMovie())
                .trailerId(adminTrailerRequestDTO.getTrailerId())
                .trailerUrls(adminTrailerRequestDTO.getMovieTrailer())
                .build();
    }
}
