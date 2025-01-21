package com.lobox.interview.service;

import com.lobox.interview.dto.MovieDTO;
import com.lobox.interview.dto.YearMaxRateDTO;
import com.lobox.interview.model.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MovieService {

    void addMovie(MovieDTO movieDTO);

    Page<Movie> getMovies(Pageable pageable);

    List<String> findTitlesByDirectorAndWriterSameAndAlive();

    List<String> findTitlesByTwoActors(String actor1, String actor2);

    List<YearMaxRateDTO> getBestMoviesByGenre(String genre);
}
