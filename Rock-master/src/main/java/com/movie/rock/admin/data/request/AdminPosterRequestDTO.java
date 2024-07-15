package com.movie.rock.admin.data.request;


import com.movie.rock.movie.data.entity.MovieEntity;
import com.movie.rock.movie.data.entity.MoviePostersEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AdminPosterRequestDTO {

    private Long posterId;
    private MovieEntity movie;  //영화ID
    private String moviePoster; //포스터 주소

    //생성자
    private AdminPosterRequestDTO(Long posterId,MovieEntity movie,String moviePoster){
        this.posterId = posterId;
        this.movie = movie;
        this.moviePoster = moviePoster;
    }

    //생성자에 넣을 데이터
    @Builder
    public static MoviePostersEntity ofEntity(AdminPosterRequestDTO adminPosterRequestDTO){
        return MoviePostersEntity.builder()
                .posterId(adminPosterRequestDTO.getPosterId())
                .movie(adminPosterRequestDTO.getMovie())
                .posterUrls(adminPosterRequestDTO.getMoviePoster())
                .build();
    }

}
