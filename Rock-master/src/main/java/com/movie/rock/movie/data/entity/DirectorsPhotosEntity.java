package com.movie.rock.movie.data.entity;

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
    private Long directorPhotoId;

    @Column(name = "director_id", insertable = false, updatable = false) //중복 매핑 방지
    private Long directorId;

    @Column(name = "director_photo")
    private String directorPhoto;

    @ManyToOne
    @JoinColumn(name = "director_id", referencedColumnName = "director_id") //중복 매핑 방지
    private DirectorsEntity director;

    @Builder
    public DirectorsPhotosEntity(Long directorId, String directorPhoto, DirectorsEntity director) {
        this.directorId = directorId;
        this.directorPhoto = directorPhoto;
        this.director = director;
    }
}
