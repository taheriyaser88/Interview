package com.lobox.interview.controller;

import com.lobox.interview.dto.MovieDTO;
import com.lobox.interview.dto.YearMaxRateDTO;
import com.lobox.interview.model.Movie;
import com.lobox.interview.service.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
@Tag(name = "Interview API", description = "APIs refer to the requested parts of project.")
public class MovieController {

    @Autowired
    private MovieService movieService;

    private int requestCount = 0;

    @Operation(summary = "Add a new movie")
    @PostMapping
    public ResponseEntity<String> addMovie(@RequestBody MovieDTO movieDTO) {
        incrementRequestCount();
        movieService.addMovie(movieDTO);
        return ResponseEntity.ok("Movie added successfully");
    }

    @Operation(summary = "Get paginated list of movies")
    @GetMapping
    public ResponseEntity<Page<Movie>> getMovies(@RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "10") int size) {
        incrementRequestCount();
        Pageable pageable = PageRequest.of(page, size);
        Page<Movie> movies = movieService.getMovies(pageable);
        return ResponseEntity.ok(movies);
    }

    @Operation(summary = "Get movies where the director and writer are the same person and alive")
    @GetMapping("/director-writer")
    public ResponseEntity<List<String>> getMoviesByDirectorAndWriter() {
        incrementRequestCount();
        List<String> movies = movieService.findTitlesByDirectorAndWriterSameAndAlive();
        return ResponseEntity.ok(movies);
    }

    @Operation(summary = "Get movies where two actors acted together")
    @GetMapping("/actors")
    public ResponseEntity<List<String>> getMoviesByActors(@RequestParam String actor1, @RequestParam String actor2) {
        incrementRequestCount();
        List<String> movies = movieService.findTitlesByTwoActors(actor1, actor2);
        return ResponseEntity.ok(movies);
    }

    @Operation(summary = "Get best movies by genre for each year")
    @GetMapping("/genre")
    public ResponseEntity<List<YearMaxRateDTO>> getBestMoviesByGenre(@RequestParam String genre) {
        incrementRequestCount();
        List<YearMaxRateDTO> movies = movieService.getBestMoviesByGenre(genre);
        return ResponseEntity.ok(movies);
    }

    @Operation(summary = "Get request count")
    @GetMapping("/request-count")
    public int getRequestCount() {
        //Getting this number is request too. so we have to increment the requestCount before sending it.
        incrementRequestCount();
        return requestCount;
    }

    private void incrementRequestCount() {
        requestCount++;
    }
}


