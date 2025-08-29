package com.movies.moviemanager.service;

import com.movies.moviemanager.dao.MovieRepository;
import com.movies.moviemanager.entity.Movie;
import com.movies.moviemanager.exception.MovieNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

    @Mock
    private MovieRepository repo;

    @InjectMocks
    private MovieService service;

    private Movie movie;

    @BeforeEach
    void setup() {
        movie = new Movie("1", "Inception", "Nolan", 2010, "Sci-Fi", 9);
    }

    @Test
    void getMovies_shouldReturnPageOfMovies() {
        PageRequest pageable = PageRequest.of(0, 2);
        Page<Movie> page = new PageImpl<>(List.of(movie));

        when(repo.findAll(pageable)).thenReturn(page);

        Page<Movie> result = service.getMovies(pageable);

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getTitle()).isEqualTo("Inception");
        verify(repo).findAll(pageable);
    }

    @Test
    void getAllMovies_shouldReturnListOfMovies() {
        when(repo.findAll()).thenReturn(Arrays.asList(movie));

        List<Movie> result = service.getAllMovies();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getDirector()).isEqualTo("Nolan");
        verify(repo).findAll();
    }

    @Test
    void getMovieById_shouldReturnMovie_whenExists() {
        when(repo.findById("1")).thenReturn(Optional.of(movie));

        Movie found = service.getMovieById("1");

        assertThat(found.getTitle()).isEqualTo("Inception");
        verify(repo).findById("1");
    }

    @Test
    void getMovieById_shouldThrowException_whenNotFound() {
        when(repo.findById("99")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getMovieById("99"))
                .isInstanceOf(MovieNotFoundException.class)
                .hasMessageContaining("Movie not found with id: 99");
    }

    @Test
    void createMovie_shouldSaveNewMovie() {
        Movie newMovie = new Movie(null, "Tenet", "Nolan", 2020, "Sci-Fi", 8);
        Movie saved = new Movie("2", "Tenet", "Nolan", 2020, "Sci-Fi", 8);

        when(repo.save(any(Movie.class))).thenReturn(saved);

        Movie result = service.createMovie(newMovie);

        assertThat(result.getId()).isEqualTo("2");
        assertThat(result.getTitle()).isEqualTo("Tenet");
        verify(repo).save(any(Movie.class));
    }

    @Test
    void updateMovie_shouldModifyAndSaveMovie() {
        Movie updated = new Movie("1", "Interstellar", "Nolan", 2014, "Sci-Fi", 10);

        when(repo.findById("1")).thenReturn(Optional.of(movie));
        when(repo.save(any(Movie.class))).thenReturn(updated);

        Movie result = service.updateMovie("1", updated);

        assertThat(result.getTitle()).isEqualTo("Interstellar");
        assertThat(result.getRating()).isEqualTo(10);
        verify(repo).save(any(Movie.class));
    }

    @Test
    void deleteMovie_shouldDeleteWhenExists() {
        when(repo.existsById("1")).thenReturn(true);

        service.deleteMovie("1");

        verify(repo).deleteById("1");
    }

    @Test
    void deleteMovie_shouldThrowExceptionWhenNotExists() {
        when(repo.existsById("99")).thenReturn(false);

        assertThatThrownBy(() -> service.deleteMovie("99"))
                .isInstanceOf(MovieNotFoundException.class)
                .hasMessageContaining("Movie not found with id: 99");

        verify(repo, never()).deleteById("99");
    }
}
