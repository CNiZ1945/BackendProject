package com.movie.rock.movie.controller;

import com.movie.rock.movie.data.response.MovieResponseDTO;
import com.movie.rock.movie.service.MovieDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movies")
public class MovieController {

    //    @GetMapping("/{movieId")
    @Autowired
    private MovieDetailServiceImpl movieDetailServiceImpl;

    @GetMapping("/{movieId}")
    public ResponseEntity<MovieResponseDTO> getMovieDetail(@PathVariable Long movieId) {
        try {
            MovieResponseDTO movie = movieDetailServiceImpl.getMovieDetail(movieId);
            return ResponseEntity.ok(movie);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(null);
        }
    }
}
