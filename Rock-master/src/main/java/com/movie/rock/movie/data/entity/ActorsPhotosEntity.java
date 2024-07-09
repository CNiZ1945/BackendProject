package com.movie.rock.movie.data.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "actors_photos")
public class ActorsPhotosEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "a_photo_id")
    private Long actorPhotoId;

    @Column(name = "actor_id", insertable = false, updatable = false) //중목 매핑 방지
    private Long actorId;

    @Column(name = "actor_photo")
    private String actorPhoto;

    @ManyToOne
    @JoinColumn(name = "actor_id", referencedColumnName = "actor_id")
    private ActorsEntity actor;

    @Builder
    public ActorsPhotosEntity(Long actorId, String actorPhoto, ActorsEntity actor) {
        this.actorId = actorId;
        this.actorPhoto = actorPhoto;
        this.actor = actor;
    }
}

// actorId에 Id값을 준 이유
//현재 데이터 모델이 실제 비즈니스 요구사항과 일치함
//필요시 쉽게 확장 가능한 구조임을 설명
//성능과 데이터 일관성 측면에서 현재 구조의 이점
