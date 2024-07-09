package com.movie.rock.movie.controller;

import com.movie.rock.admin.service.AdminMovieListService;
import com.movie.rock.movie.data.entity.MovieEntity;
import com.movie.rock.movie.data.response.MovieResponseDTO;
import com.movie.rock.movie.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class MovieController {

    private final AdminMovieListService adminMovieListService;

//    @GetMapping("/movieList")
//    public ResponseEntity<Page<MovieResponseDTO>>
//    MovieList(@PageableDefault(size = 10, sort = "movieId",direction = Sort.Direction.DESC) Pageable pageable) {
////        Page<MovieResponseDTO> listDto = MovieService.
////    }
////    @GetMapping("/{movieId")



}
