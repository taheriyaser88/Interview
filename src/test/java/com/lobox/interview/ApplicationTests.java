package com.lobox.interview;

import com.lobox.interview.model.Actor;
import com.lobox.interview.model.Movie;
import com.lobox.interview.repository.ActorRepository;
import com.lobox.interview.repository.MovieRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ApplicationTests {

	@Autowired
	private MovieRepository movieRepository;

	@Autowired
	private ActorRepository actorRepository;

    @Transactional
	@Test
	void testMovieRepository() {
		Movie movie = new Movie();
		movie.setTitle("Inception");
		movie.setDirector("Nolan");
		movie.setWriter("Nolan");
		movie.setGenre("Sci-Fi");
		movie.setReleaseYear(2010);
		movie.setRating(8.8);
		movie.setVotes(2000000);
		movie.setAlive(true);

		Actor actor1 = new Actor();
		actor1.setName("DiCaprio");
		actor1 = actorRepository.save(actor1);
		Actor actor2 = new Actor();
		actor2.setName("Gordon-Levitt");
		actor2 = actorRepository.save(actor2);
		List<Actor> actors = new ArrayList<>();
		actors.add(actor1);
		actors.add(actor2);

		movie.setActors(actors);

		movieRepository.save(movie);

		List<String> movies = movieRepository.findTitlesByDirectorAndWriterSameAndAlive();

		assertThat(movies).isNotEmpty();
	}
}
