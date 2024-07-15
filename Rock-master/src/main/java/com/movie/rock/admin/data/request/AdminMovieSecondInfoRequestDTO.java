package com.movie.rock.admin.data.request;


import com.movie.rock.movie.data.entity.MovieEntity;
import com.movie.rock.movie.data.entity.MovieFilmEntity;
import com.movie.rock.movie.data.entity.MoviePostersEntity;
import com.movie.rock.movie.data.entity.MovieTrailersEntity;
import com.movie.rock.movie.data.response.MovieInfoResponseDTO.PosterResponseDTO;
import com.movie.rock.movie.data.response.MovieInfoResponseDTO.TrailerResponseDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class AdminMovieSecondInfoRequestDTO {
    private Long movieId;
    private String movieTitle;
    private List<TrailerResponseDTO> trailer;
    private MovieFilmEntity movieFilm;
    private List<PosterResponseDTO> poster;

    //생성자
    public AdminMovieSecondInfoRequestDTO(Long movieId,String movieTitle,List<TrailerResponseDTO> trailer,MovieFilmEntity movieFilm
                                          ,List<PosterResponseDTO> poster){
        this.movieId=movieId;
        this.movieTitle = movieTitle;
        this.trailer = trailer;
        this.movieFilm = movieFilm;
        this.poster = poster;
    }

    //생성자에 넣을 데이터
    @Builder
    public static MovieEntity ofEntity(AdminMovieSecondInfoRequestDTO adminMovieSecondInfoRequestDTO){
        return MovieEntity.builder()
                .movieId(adminMovieSecondInfoRequestDTO.getMovieId())
                .movieTitle(adminMovieSecondInfoRequestDTO.getMovieTitle())
                .trailer(adminMovieSecondInfoRequestDTO.getTrailer().stream()
                        .map(trailerDto -> MovieTrailersEntity.builder()
                                .trailerId(trailerDto.getTrailerId())
                                .trailerUrls(trailerDto.getMovieTrailer())
                                .build())
                        .collect(Collectors.toList()))
                .movieFilm(adminMovieSecondInfoRequestDTO.getMovieFilm())
                .poster(adminMovieSecondInfoRequestDTO.getPoster().stream()
                        .map(posterDto -> MoviePostersEntity.builder()
                                .posterId(posterDto.getPosterId())
                                .posterUrls(posterDto.getMoviePoster())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
}
