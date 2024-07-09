package com.movie.rock.movie.controller;

import com.movie.rock.file.service.AdminFileStorageService;
import com.movie.rock.movie.data.request.MovieRequestDTO;
import com.movie.rock.movie.data.response.MovieInfoResponseDTO;
import com.movie.rock.movie.data.response.MovieResponseDTO;
import com.movie.rock.movie.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/movie")
public class MovieFileUploadController {

    private final MovieService movieService;
    private final AdminFileStorageService adminFileStorageService;

    // 영화 정보 입력 폼을 보여주는 메서드
    @GetMapping("/upload")
    public String showUploadForm(Model model) {
        // 새로운 MovieRequestDTO 객체를 생성하고 "movieRequest"라는 키로 Model에 저장
        model.addAttribute("movieRequest", new MovieRequestDTO());
        // "admin/movie-upload" 뷰를 렌더링하도록 지시
        return "admin/movie-upload";
    }

    // 영화 정보를 저장하는 메서드
    @PostMapping("/upload")
    public String uploadMovie(MovieRequestDTO movieRequestDTO,
                              List<MultipartFile> posterFiles,
                              List<MultipartFile> trailerFiles,
                              MultipartFile movieFile) {
        // 영화 정보 저장
        MovieResponseDTO savedMovie = movieService.addMovie(movieRequestDTO);

        // 포스터 파일 업로드
        for (MultipartFile posterFile : posterFiles) {
            String posterUrl = adminFileStorageService.uploadFile(posterFile);
            savedMovie.getPoster().add(MovieInfoResponseDTO.PosterResponseDTO.builder()
                    .moviePoster(posterUrl)
                    .build());
        }

        // 트레일러 파일 업로드
        for (MultipartFile trailerFile : trailerFiles) {
            String trailerUrl = adminFileStorageService.uploadFile(trailerFile);
            savedMovie.getTrailer().add(MovieInfoResponseDTO.TrailerResponseDTO.builder()
                    .movieTrailer(trailerUrl)
                    .build());
        }

        // 영화 파일 업로드
        String movieFilmUrl = adminFileStorageService.uploadFile(movieFile);
        savedMovie.setMovieFilm(MovieInfoResponseDTO.FilmResponseDTO.builder()
                .movieFilm(movieFilmUrl)
                .build());

        // 저장이 완료되면 /admin/movie/list로 리다이렉트
        return "redirect:/admin/movie/list";
    }

    @GetMapping("/{movieId}/delete")
    public String deleteMovie(@PathVariable Long movieId) {
        MovieResponseDTO movie = movieService.getMovie(movieId);

        // 영화 정보 삭제
        movieService.deleteMovie(movieId);

        // 관련 파일 삭제
        for (MovieInfoResponseDTO.PosterResponseDTO poster : movie.getPoster()) {
            adminFileStorageService.deleteFile(poster.getPosterUrl());
        }
        for (MovieInfoResponseDTO.TrailerResponseDTO trailer : movie.getTrailer()) {
            adminFileStorageService.deleteFile(trailer.getTrailerUrl());
        }
        if (movie.getMovieFilm() != null) {
            adminFileStorageService.deleteFile(movie.getMovieFilm().getFilmUrl());
        }

        return "redirect:/admin/movie/list";
    }
}