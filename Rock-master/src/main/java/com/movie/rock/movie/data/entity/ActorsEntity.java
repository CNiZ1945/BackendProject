package com.movie.rock.movie.data.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "actors")
public class ActorsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "actor_id")
    private Long actorId;

    @Column(name = "actor_name")
    private String actorName;

    @Column(name = "actor_birth")
    private Integer actorBirth;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "actor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MovieActorsEntity> movieActors;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "actor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ActorsPhotosEntity> actorPhotos;

    @Builder
    public ActorsEntity(Long actorId, String actorName, Integer actorBirth, List<MovieActorsEntity> movieActors, List<ActorsPhotosEntity> actorPhotos) {
        this.actorId = actorId;
        this.actorName = actorName;
        this.actorBirth = actorBirth;
        this.movieActors = movieActors;
        this.actorPhotos = actorPhotos;
    }
    //배우 사진 추가 메서드
    public void addPhoto(String photoUrl) {
        // 1. actorPhotos 리스트가 null인지 확인
        if (this.actorPhotos == null) {
            this.actorPhotos = new ArrayList<>();
        }

        // 2. 새로운 ActorsPhotosEntity 객체 생성
        ActorsPhotosEntity newPhoto = ActorsPhotosEntity.builder()
                .actorId(this.actorId)
                .actorPhoto(photoUrl)
                .actor(this)
                .build();

        // 3. 생성된 객체를 actorPhotos 리스트에 추가
        this.actorPhotos.add(newPhoto);
    }

    //배우 사진 삭제 메서드
    public void removePhoto(ActorsPhotosEntity photos){
        if(this.actorPhotos != null && photos != null && photos.getActor().getActorId() == this.actorId)
        {
            this.actorPhotos.remove(photos);
        }else{
            throw new IllegalArgumentException("이 배우의 사진이 존재하지 않습니다.");
        }
    }

    //배우 사진 수정 메서드
    public void updatePhoto(ActorsPhotosEntity updatedPhoto) {
        if (updatedPhoto != null && this.actorPhotos != null) {
            this.actorPhotos.removeIf(photo -> photo.getActorId() == updatedPhoto.getActorId());
            this.actorPhotos.add(updatedPhoto);
        }
    }

}
