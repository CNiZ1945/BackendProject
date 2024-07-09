package com.movie.rock.movie.service;

import com.movie.rock.movie.data.request.MovieRequestDTO;
import com.movie.rock.movie.data.response.MovieResponseDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MovieService {


    MovieResponseDTO addMovie(MovieRequestDTO movieRequestDTO);
    MovieResponseDTO updateMovie(int movieId, MovieRequestDTO requestDTO);
    @Transactional
    MovieResponseDTO updateMovie(Long movieId, MovieRequestDTO requestDTO);
    void deleteMovie(int movieId);

    void deleteMovie(Long movieId);


    MovieResponseDTO getMovie(int movieId);


    MovieResponseDTO getMovie(Long movieId);

    List<MovieResponseDTO> getAllMovies();
}