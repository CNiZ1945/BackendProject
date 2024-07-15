package com.movie.rock.movie.service;

import com.movie.rock.common.MovieException;
import com.movie.rock.common.MovieException.MemberNotFoundException;
import com.movie.rock.common.MovieException.MovieNotFoundException;
import com.movie.rock.member.data.MemberEntity;
import com.movie.rock.member.data.MemberRepository;
import com.movie.rock.movie.data.entity.MovieEntity;
import com.movie.rock.movie.data.entity.MovieFavorEntity;
import com.movie.rock.movie.data.entity.MoviePostersEntity;
import com.movie.rock.movie.data.repository.MovieFavorRepository;
import com.movie.rock.movie.data.repository.MoviePostersRepository;
import com.movie.rock.movie.data.repository.MovieRepository;
import com.movie.rock.movie.data.request.MovieFavorRequestDTO;
import com.movie.rock.movie.data.response.MovieFavorResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieFavorServiceImpl implements MovieFavorService {

    private final MovieFavorRepository movieFavorRepository;
    private final MovieRepository movieRepository;
    private final MoviePostersRepository moviePostersRepository;
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public MovieFavorResponseDTO addFavorites(Long memNum, MovieFavorRequestDTO movieFavorRequestDTO) {
        MovieEntity movie = movieRepository.findByMovieId(movieFavorRequestDTO.getMovieId())
                .orElseThrow(MovieNotFoundException::new);

        MemberEntity member = memberRepository.findByMemNum(memNum)
                .orElseThrow(MemberNotFoundException::new);

        if (!isFavoritedBy(movie.getMovieId(), memNum)) {
            MovieFavorEntity movieFavor = new MovieFavorEntity(member, movie);
            movieFavorRepository.save(movieFavor);
        }

        return getFavoritesStatus(memNum, movie.getMovieId());
    }

    @Override
    @Transactional
    public MovieFavorResponseDTO removeFavorites(Long memNum, Long movieId) {
        movieRepository.findByMovieId(movieId)
                .orElseThrow(MovieNotFoundException::new);

        movieFavorRepository.deleteByMemberMemNumAndMovieMovieId(memNum, movieId);

        return getFavoritesStatus(memNum, movieId);
    }

    @Override
    public List<MovieFavorResponseDTO> getFavoritesMovies(Long memNum) {
        List<MovieFavorEntity> favorites = movieFavorRepository.findByMemberMemNum(memNum);

        return favorites.stream()
                .map(favor -> getFavoritesStatus(memNum, favor.getMovie().getMovieId()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isFavoritedBy(Long movieId, Long memNum) {
        return movieFavorRepository.existsByMemberMemNumAndMovieMovieId(memNum, movieId);
    }

    @Override
    @Transactional(readOnly = true)
    public Long getTotalFavoritesCount(Long movieId) {
        return movieFavorRepository.countByMovieMovieId(movieId);
    }

    @Override
    public MovieFavorResponseDTO getFavoritesStatus(Long memNum, Long movieId) {
        MovieEntity movie = movieRepository.findByMovieId(movieId)
                .orElseThrow(MovieException.MovieNotFoundException::new);
        MemberEntity member = memberRepository.findByMemNum(memNum)
                .orElseThrow(MovieException.MemberNotFoundException::new);
        boolean isFavorite = isFavoritedBy(movieId, memNum);
        Long totalFavorCount = getTotalFavoritesCount(movieId);

        return new MovieFavorResponseDTO(
                movie.getMovieId(),
                movie.getMovieTitle(),
                movie.getPoster().stream()
                        .findFirst()
                        .map(MoviePostersEntity::getPosterUrls)
                        .orElse(null),
                isFavorite,
                totalFavorCount,
                member.getMemName()
        );
    }

}