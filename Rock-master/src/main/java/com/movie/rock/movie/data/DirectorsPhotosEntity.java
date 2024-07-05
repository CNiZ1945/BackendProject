package com.movie.rock.movie.data;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "directors_photos")
public class DirectorsPhotosEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "d_photo_id")
    private int directorPhotoId;

    @Column(name = "director_id")
    private int directorId;

    @Column(name = "director_photo")
    private String directorPhoto;

    @ManyToOne
    @JoinColumn(name = "director_id", referencedColumnName = "director_id")
    private DirectorsEntity director;

    @Builder
    public DirectorsPhotosEntity(int directorId, String directorPhoto, DirectorsEntity director) {
        this.directorId = directorId;
        this.directorPhoto = directorPhoto;
        this.director = director;
    }
}
