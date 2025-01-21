package com.lobox.interview.service.impl;

import com.lobox.interview.dto.MovieDTO;
import com.lobox.interview.dto.YearMaxRateDTO;
import com.lobox.interview.model.Actor;
import com.lobox.interview.model.Movie;
import com.lobox.interview.repository.ActorRepository;
import com.lobox.interview.repository.MovieRepository;
import com.lobox.interview.service.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ActorRepository actorRepository;

    private static final Logger log = LoggerFactory.getLogger(MovieServiceImpl.class);

    @Transactional
    @Override
    public void addMovie(MovieDTO movieDTO) {

        log.info("Adding new movie: {}", movieDTO.getTitle());
        List<Actor> actors = new ArrayList<>();
        for(Actor actor : movieDTO.getActors()){
            Optional<Actor> actor1  = actorRepository.findByName(actor.getName());
            if(actor1.isPresent()){
                actors.add(actor1.get());
            } else {
                actors.add(actorRepository.save(actor));
            }
        }
        Movie movie = new Movie();
        movie.setTitle(movieDTO.getTitle());
        movie.setDirector(movieDTO.getDirector());
        movie.setWriter(movieDTO.getWriter());
        movie.setGenre(movieDTO.getGenre());
        movie.setReleaseYear(movieDTO.getYear());
        movie.setRating(movieDTO.getRating());
        movie.setVotes(movieDTO.getVotes());
        movie.setActors(actors);
        movieRepository.save(movie);
    }

    @Override
    public Page<Movie> getMovies(Pageable pageable) {
        log.info("Fetching paginated list of movies");
        return movieRepository.findAll(pageable);
    }

    @Override
    public List<String> findTitlesByDirectorAndWriterSameAndAlive() {
        log.info("Fetching movies where director and writer are the same");
        return movieRepository.findTitlesByDirectorAndWriterSameAndAlive();
    }

    @Override
    public List<String> findTitlesByTwoActors(String actor1, String actor2) {
        log.info("Fetching movies where {} and {} acted together", actor1, actor2);
        return movieRepository.findTitlesByTwoActors(actor1, actor2);
    }

    @Override
    public List<YearMaxRateDTO> getBestMoviesByGenre(String genre) {
        log.info("Fetching best movies by genre: {}", genre);
        return movieRepository.findBestTitlesByGenre(genre);
    }
}
