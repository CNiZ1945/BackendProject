package com.movie.rock.movie.data.response;

import com.movie.rock.movie.data.entity.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class MovieInfoResponseDTO {

    @Getter
    @NoArgsConstructor
    public static class ActorResponseDTO {

        private Long actorId;
        private String actorName;
        private Integer actorBirth;
        private String actorPhoto;

        @Builder
        public ActorResponseDTO(Long actorId, String actorName, Integer actorBirth, String actorPhoto) {
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

        private Long directorId;
        private String directorName;
        private Integer directorBirth;
        private String directorPhoto;

        @Builder
        public DirectorResponseDTO(Long directorId, String directorName, Integer directorBirth, String directorPhoto) {
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
    public static class GenreResponseDTO {
        private Long genreId;
        private String genreName;

        @Builder
        public GenreResponseDTO(Long genreId, String genreName) {
            this.genreId = genreId;
            this.genreName = genreName;
        }

        public static GenreResponseDTO fromEntity(GenresEntity genres) {
            return GenreResponseDTO.builder()
                    .genreId(genres.getGenreId())
                    .genreName(genres.getGenreName())
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor
    public static class PosterResponseDTO {
        private Long posterId;
        private String moviePoster;

        @Builder
        public PosterResponseDTO(Long posterId, String moviePoster) {
            this.posterId = posterId;
            this.moviePoster = moviePoster;
        }

        public static PosterResponseDTO fromEntity(MoviePostersEntity poster) {
            return PosterResponseDTO.builder()
                    .posterId(poster.getPosterId())
                    .moviePoster(poster.getPosterUrls())
                    .build();
        }

        public String getPosterUrl() {
            return moviePoster;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class TrailerResponseDTO {
        private Long trailerId;
        private String movieTrailer;

        @Builder
        public TrailerResponseDTO(Long trailerId, String movieTrailer) {
            this.trailerId = trailerId;
            this.movieTrailer = movieTrailer;
        }

        public static TrailerResponseDTO fromEntity(MovieTrailersEntity trailer) {
            return TrailerResponseDTO.builder()
                    .trailerId(trailer.getTrailerId())
                    .movieTrailer(trailer.getTrailerUrls())
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor
    public static class FilmResponseDTO {
        private Long filmId;
        private String movieFilm;

        @Builder
        public FilmResponseDTO(Long filmId, String movieFilm) {
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

    @Getter
    @NoArgsConstructor
    public static class ActorsPhotosResponseDTO {
        private Long actorPhotoId;
        private Long actorId;
        private String actorPhoto;

        @Builder
        public ActorsPhotosResponseDTO(Long actorPhotoId, Long actorId, String actorPhoto) {
            this.actorPhotoId = actorPhotoId;
            this.actorId = actorId;
            this.actorPhoto = actorPhoto;
        }

        public static ActorsPhotosResponseDTO fromEntity(ActorsPhotosEntity actorsPhotos) {
            return ActorsPhotosResponseDTO.builder()
                    .actorPhotoId(actorsPhotos.getActorPhotoId())
                    .actorId(actorsPhotos.getActorId())
                    .actorPhoto(actorsPhotos.getActorPhoto())
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor
    public static class DirectorsPhotosResponseDTO {
        private Long directorPhotoId;
        private Long directorId;
        private String directorPhoto;

        @Builder
        public DirectorsPhotosResponseDTO(Long directorPhotoId, Long directorId, String directorPhoto) {
            this.directorPhotoId = directorPhotoId;
            this.directorId = directorId;
            this.directorPhoto = directorPhoto;
        }

        public static DirectorsPhotosResponseDTO fromEntity(DirectorsPhotosEntity directorsPhotos) {
            return DirectorsPhotosResponseDTO.builder()
                    .directorPhotoId(directorsPhotos.getDirectorPhotoId())
                    .directorId(directorsPhotos.getDirectorId())
                    .directorPhoto(directorsPhotos.getDirectorPhoto())
                    .build();
        }
    }
}
