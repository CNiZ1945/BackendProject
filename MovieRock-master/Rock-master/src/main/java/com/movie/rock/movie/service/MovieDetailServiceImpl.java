package com.movie.rock.movie.service;

import com.movie.rock.movie.data.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieDetailServiceImpl {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private MovieActorsRepository movieActorsRepository;

    @Autowired
    private MovieDirectorsRepository movieDirectorsRepository;

    @Autowired
    private ActorsPhotosRepository actorsPhotosRepository;

    @Autowired
    private DirectorsPhotosRepository directorsPhotosRepository;

    @Autowired
    private MoviePostersRepository moviePostersRepository;

    @Autowired
    private MovieTrailersRepository movieTrailersRepository;

    @Autowired
    private MovieFilmRepository movieFilmRepository;
}
