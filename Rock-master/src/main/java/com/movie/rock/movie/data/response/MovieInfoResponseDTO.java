package com.movie.rock.movie.data.response;

import com.movie.rock.movie.data.entity.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class MovieInfoResponseDTO {

    @Getter
    @NoArgsConstructor
    public static class ActorResponseDTO {

        private int actorId;
        private String actorName;
        private Integer actorBirth;
        private String actorPhoto;

        @Builder
        public ActorResponseDTO(int actorId, String actorName, Integer actorBirth, String actorPhoto) {
            this.actorId = actorId;
            this.actorName = actorName;
            this.actorBirth = actorBirth;
            this.actorPhoto = actorPhoto;
        }

        public static ActorResponseDTO fromEntity(ActorsEntity actors) {
            return ActorResponseDTO.builder()
                    .actorId(actors.getActorId())
                    .actorName(actors.getActorName())
                    .actorBirth(actors.getActorBirth())
                    .actorPhoto(actors.getActorPhotos().toString())
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor
    public static class DirectorResponseDTO {

        private int directorId;
        private String directorName;
        private Integer directorBirth;
        private String directorPhoto;

        @Builder
        public DirectorResponseDTO(int directorId, String directorName, Integer directorBirth, String directorPhoto) {
            this.directorId = directorId;
            this.directorName = directorName;
            this.directorBirth = directorBirth;
            this.directorPhoto = directorPhoto;
        }

        public static DirectorResponseDTO fromEntity(DirectorsEntity director) {
            return DirectorResponseDTO.builder()
                    .directorId(director.getDirectorId())
                    .directorName(director.getDirectorName())
                    .directorBirth(director.getDirectorBirth())
                    .directorPhoto(director.getDirectorPhotos().toString())
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor
    public static class PosterResponseDTO {
        private int posterId;
        private String moviePoster;

        @Builder
        public PosterResponseDTO(int posterId, String moviePoster) {
            this.posterId = posterId;
            this.moviePoster = moviePoster;
        }

        public static PosterResponseDTO fromEntity(MoviePostersEntity poster) {
            return PosterResponseDTO.builder()
                    .posterId(poster.getPosterId())
                    .moviePoster(poster.getMoviePoster())
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor
    public static class TrailerResponseDTO {
        private int trailerId;
        private String movieTrailer;

        @Builder
        public TrailerResponseDTO(int trailerId, String movieTrailer) {
            this.trailerId = trailerId;
            this.movieTrailer = movieTrailer;
        }

        public static TrailerResponseDTO fromEntity(MovieTrailersEntity trailer) {
            return TrailerResponseDTO.builder()
                    .trailerId(trailer.getTrailerId())
                    .movieTrailer(trailer.getMovieTrailer())
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor
    public static class FilmResponseDTO {
        private int filmId;
        private String movieFilm;

        @Builder
        public FilmResponseDTO(int filmId, String movieFilm) {
            this.filmId = filmId;
            this.movieFilm = movieFilm;
        }

        public static FilmResponseDTO fromEntity(MovieFilmEntity movieFilm) {
            return FilmResponseDTO.builder()
                    .filmId(movieFilm.getFilmId())
                    .movieFilm(movieFilm.getMovieFilm())
                    .build();
        }
    }


}
