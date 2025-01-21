package com.lobox.interview.service;

import com.lobox.interview.model.Movie;
import com.lobox.interview.repository.MovieRepository;
import com.lobox.interview.service.impl.MovieServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieServiceImpl movieService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetMovies() {
        Pageable pageable = PageRequest.of(0, 10);
        Movie movie = new Movie();
        movie.setTitle("Inception");

        Page<Movie> moviePage = new PageImpl<>(Collections.singletonList(movie));
        when(movieRepository.findAll(pageable)).thenReturn(moviePage);

        Page<Movie> result = movieService.getMovies(pageable);
        assertThat(result.getContent()).isNotEmpty();
        assertThat(result.getContent().get(0).getTitle()).isEqualTo("Inception");
    }
}
