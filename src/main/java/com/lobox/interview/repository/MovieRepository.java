package com.lobox.interview.repository;

import com.lobox.interview.dto.MovieDTO;
import com.lobox.interview.dto.YearMaxRateDTO;
import com.lobox.interview.model.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Query(value = "SELECT m.title FROM movie m WHERE m.director = m.writer AND m.is_alive = true", nativeQuery = true)
    List<String> findTitlesByDirectorAndWriterSameAndAlive();

    @Query("SELECT m.title FROM Movie m WHERE :actor1 MEMBER OF m.actors AND :actor2 MEMBER OF m.actors")
    List<String> findTitlesByTwoActors(String actor1, String actor2);

    @Query("SELECT m.releaseYear as year, MAX(m.rating) as maxRate FROM Movie m WHERE m.genre = :genre GROUP BY m.releaseYear ORDER BY m.releaseYear")
    List<YearMaxRateDTO> findBestTitlesByGenre(String genre);

    Page<Movie> findAll(Pageable pageable);
}
