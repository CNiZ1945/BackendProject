package com.movie.rock.movie.service;

import com.movie.rock.movie.data.response.MovieResponseDTO;

public interface MovieService {
    MovieResponseDTO getMovieDetail(Long movieId);
}
