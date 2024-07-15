package com.movie.rock.admin.service;

import com.movie.rock.admin.data.request.*;
import com.movie.rock.admin.data.response.*;
import com.movie.rock.common.MovieException.MovieNotFoundException;
import com.movie.rock.movie.data.entity.*;
import com.movie.rock.movie.data.repository.*;
import com.movie.rock.movie.data.response.MovieInfoResponseDTO.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@RequiredArgsConstructor
public class AdminMovieServiceImpl implements AdminMovieService {


    private final MovieRepository movieRepository;

    private final MovieActorsRepository movieActorsRepository;

    private final MovieDirectorsRepository movieDirectorsRepository;

    private final MoviePostersRepository moviePostersRepository;

    private final MovieTrailersRepository movieTrailersRepository;

//    private final MovieFilmRepository movieFilmRepository;
//
//    private final MovieGenresRepository movieGenresRepository;

    private final ActorsRepository actorsRepository;

    private final DirectorsRepository directorsRepository;

    private final GenresRepository genresRepository;
//
//    private final AdminMovieListRepository adminMovieListRepository;
//
//    private final AdminAddMovieRepository adminAddMovieRepository;

    // 페이징 처리된 영화 목록을 조회합니다.
    @Override
    public Page<AdminMovieListResponseDTO> getMovieList(Pageable pageable) {
        Page<MovieEntity> moviePage = movieRepository.findAll(pageable);
        return moviePage.map(AdminMovieListResponseDTO::fromEntity);
    }


    //영화 상세보기
    @Override
    public AdminMovieDetailResponseDTO getMovieDetails(Long movieId) {
        MovieEntity findMovie = movieRepository.findById(movieId)
                .orElseThrow(MovieNotFoundException::new);

        return AdminMovieDetailResponseDTO.fromEntity(findMovie);
    }

    //영화 추가(첫번째 페이지)
    @Override
    public AdminMovieFirstInfoResponseDTO adminMovieFirstInfoResponseDTO(AdminMovieFirstInfoRequestDTO adminMovieFirstInfoRequestDTO)
    {
        MovieEntity movieEntity = AdminMovieFirstInfoRequestDTO.ofEntity(adminMovieFirstInfoRequestDTO);
        MovieEntity saveMovieEntity = movieRepository.save(movieEntity);
        return AdminMovieFirstInfoResponseDTO.fromEntity(saveMovieEntity);
    }

    //영화 추가(두번째 페이지)
    @Override
    public AdminMovieSecondInfoResponseDTO adminMovieSecondInfoResponseDTO(AdminMovieSecondInfoRequestDTO adminMovieSecondInfoRequestDTO)
    {
        MovieEntity movieEntity = AdminMovieSecondInfoRequestDTO.ofEntity(adminMovieSecondInfoRequestDTO);
        MovieEntity saveMovieEntity = movieRepository.save(movieEntity);
        return AdminMovieSecondInfoResponseDTO.fromEntity(saveMovieEntity);
    }


    //배우 추가
    @Override
    public ActorResponseDTO addActor(AdminActorRequestDTO adminActorRequestDTO){
        ActorsEntity actorsEntity = AdminActorRequestDTO.ofEntity(adminActorRequestDTO);

        actorsEntity = actorsRepository.save(actorsEntity);
        return ActorResponseDTO.fromEntity(actorsEntity);
    }

    //감독 추가
    @Override
    public DirectorResponseDTO addDirector(AdminDirectorRequestDTO adminDirectorRequestDTO){
        DirectorsEntity directorsEntity = AdminDirectorRequestDTO.ofEntity(adminDirectorRequestDTO); //엔티티형식으로저장

        directorsEntity = directorsRepository.save(directorsEntity);
        return DirectorResponseDTO.fromEntity(directorsEntity);//DTO형식으로 저장
    }
    //장르 추가
    @Override
    public GenreResponseDTO addGenre(AdminGenreRequestDTO adminGenreRequestDTO){
        GenresEntity genresEntity = AdminGenreRequestDTO.ofEntity(adminGenreRequestDTO);

        genresEntity = genresRepository.save(genresEntity);
        return GenreResponseDTO.fromEntity(genresEntity);
    }
    //예고편 추가
    @Override
    public TrailerResponseDTO addTrailer(AdminTrailerRequestDTO adminTrailerRequestDTO){
        MovieTrailersEntity movieTrailersEntity = AdminTrailerRequestDTO.ofEntity(adminTrailerRequestDTO);

        movieTrailersEntity = movieTrailersRepository.save(movieTrailersEntity);
        return TrailerResponseDTO.fromEntity(movieTrailersEntity);
    }
    //포스터 추가
    @Override
    public PosterResponseDTO addPoster(AdminPosterRequestDTO adminPosterRequestDTO){
        MoviePostersEntity moviePostersEntity = AdminPosterRequestDTO.ofEntity(adminPosterRequestDTO);

        moviePostersEntity = moviePostersRepository.save(moviePostersEntity);
        return PosterResponseDTO.fromEntity(moviePostersEntity);
    }
    //영화수정


    //삭제
    @Override
    @Transactional
    public void deleteMovie(Long movieId){
        movieRepository.deleteById(movieId);
    }
}
