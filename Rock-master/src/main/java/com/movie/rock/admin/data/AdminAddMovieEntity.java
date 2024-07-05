//package com.movie.rock.admin.data;
//
//import com.nimbusds.oauth2.sdk.id.Actor;
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//@Entity
//@Getter
//@Setter
//@NoArgsConstructor
//public class AdminAddMovieEntity {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "movie_id")
//    private Long movieId;
//
//
//    @Embedded
//    private AdminAddMoviePk id;
//
//    @ManyToOne
//    @MapsId("directorId")
//    @JoinColumn(name = "director_id", referencedColumnName = "director_id")
//    private Director director;
//
//    @ManyToOne
//    @MapsId("actorId")
//    @JoinColumn(name = "actor_id", referencedColumnName = "actor_id")
//    private Actor actor;
//
//    @Entity
//    public static class Director {
//        @Id
//        @GeneratedValue(strategy = GenerationType.IDENTITY)
//        @Column(name = "director_id")
//        private Long id;
//
//        private String name;
//
//        // Getter, Setter, Constructors
//    }
//
//    @Entity
//    public static class Actor {
//        @Id
//        @GeneratedValue(strategy = GenerationType.IDENTITY)
//        @Column(name = "actor_id")
//        private Long id;
//
//        private String name;
//
//        // Getter, Setter, Constructors
//    }
//}
