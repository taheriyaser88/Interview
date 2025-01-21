package com.lobox.interview.controller;

import com.lobox.interview.dto.MovieDTO;
import com.lobox.interview.model.Movie;
import com.lobox.interview.service.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MovieControllerTest {

    private MockMvc mockMvc;

    @Mock
    private MovieService movieService;

    @InjectMocks
    private MovieController movieController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(movieController).build();
    }

    @Test
    void testAddMovie() throws Exception {
        MovieDTO movieDTO = new MovieDTO();
        movieDTO.setTitle("Inception");

        mockMvc.perform(post("/api/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"Inception\", \"director\": \"Nolan\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Movie added successfully"));
    }

    @Test
    void testGetMoviesByDirectorAndWriter() throws Exception {
        Movie movie = new Movie();
        movie.setTitle("Inception");
        movie.setDirector("Nolan");
        movie.setWriter("Nolan");
        movie.setAlive(true);

        when(movieService.findTitlesByDirectorAndWriterSameAndAlive())
                .thenReturn(Collections.singletonList("Inception"));

        mockMvc.perform(get("/api/movies/director-writer")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[\"Inception\"]"));
    }
}
