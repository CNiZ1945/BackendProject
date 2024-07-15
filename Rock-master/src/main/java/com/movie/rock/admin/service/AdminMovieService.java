package com.movie.rock.admin.service;

import com.movie.rock.admin.data.request.*;
import com.movie.rock.admin.data.response.*;
import com.movie.rock.movie.data.response.MovieInfoResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminMovieService {


    //AdminMovieListController -> 페이징 처리한 영화목록 조회
    Page<AdminMovieListResponseDTO> getMovieList(Pageable pageable);

    //MovieAdminServiceImpl -> 영화 상세보기
    AdminMovieDetailResponseDTO getMovieDetails(Long movieId);

    //MovieAdminServiceImpl -> 감독 추가
    MovieInfoResponseDTO.DirectorResponseDTO addDirector(AdminDirectorRequestDTO adminDirectorRequestDTO);

    //MovieAdminServiceImpl -> 장르 추가
    MovieInfoResponseDTO.GenreResponseDTO addGenre(AdminGenreRequestDTO adminGenreRequestDTO);

    //MovieAdminServiceImpl -> 예고편 추가
    MovieInfoResponseDTO.TrailerResponseDTO addTrailer(AdminTrailerRequestDTO adminTrailerRequestDTO);

    //MovieAdminServiceImpl -> 포스터 추가
    MovieInfoResponseDTO.PosterResponseDTO addPoster(AdminPosterRequestDTO adminPosterRequestDTO);
    
    //MovieAdminServiceImpl -> 영화 추가(첫번째 페이지)
    AdminMovieFirstInfoResponseDTO adminMovieFirstInfoResponseDTO(AdminMovieFirstInfoRequestDTO adminMovieFirstInfoRequestDTO);

    //MovieAdminServiceImpl -> 영화 추가(두번째 페이지)
    AdminMovieSecondInfoResponseDTO adminMovieSecondInfoResponseDTO(AdminMovieSecondInfoRequestDTO adminMovieSecondInfoRequestDTO);

    //MovieAdminServiceImpl -> 배우 추가
    MovieInfoResponseDTO.ActorResponseDTO addActor(AdminActorRequestDTO adminActorRequestDTO);

    //MovieAdminServiceImpl -> 영화 삭제
    void deleteMovie(Long movieId);

}