package com.movies.moviemanager.controller;

import com.movies.moviemanager.entity.Movie;
import com.movies.moviemanager.service.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class MovieControllerTest {

    @Mock
    private MovieService movieService;

    @InjectMocks
    private MovieController movieController;

    private Movie movie1;
    private Movie movie2;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        movie1 = new Movie("1", "Inception", "Nolan", 2010, "Sci-Fi", 9);
        movie2 = new Movie("2", "Interstellar", "Nolan", 2014, "Sci-Fi", 10);
    }

    @Test
    void testGetMovies() {
        PageRequest pageable = PageRequest.of(0, 10);
        Page<Movie> page = new PageImpl<>(Arrays.asList(movie1, movie2));

        when(movieService.getMovies(pageable)).thenReturn(page);

        Page<Movie> result = movieController.getMovies(pageable);

        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getContent().get(0).getTitle()).isEqualTo("Inception");
        verify(movieService, times(1)).getMovies(pageable);
    }

    @Test
    void testGetAllMovies() {
        when(movieService.getAllMovies()).thenReturn(Arrays.asList(movie1, movie2));

        List<Movie> result = movieController.getAllMovies();

        assertThat(result).hasSize(2);
        assertThat(result.get(1).getTitle()).isEqualTo("Interstellar");
        verify(movieService, times(1)).getAllMovies();
    }

    @Test
    void testGetMovieById() {
        when(movieService.getMovieById("1")).thenReturn(movie1);

        Movie result = movieController.getMovieById("1");

        assertThat(result.getTitle()).isEqualTo("Inception");
        verify(movieService, times(1)).getMovieById("1");
    }

    @Test
    void testCreateMovie() {
        when(movieService.createMovie(movie1)).thenReturn(movie1);

        ResponseEntity<Movie> response = movieController.createMovie(movie1);

        assertThat(response.getBody()).isEqualTo(movie1);
        verify(movieService, times(1)).createMovie(movie1);
    }

    @Test
    void testUpdateMovie() {
        when(movieService.updateMovie("1", movie1)).thenReturn(movie1);

        ResponseEntity<Movie> response = movieController.updateMovie("1", movie1);

        assertThat(response.getBody()).isEqualTo(movie1);
        verify(movieService, times(1)).updateMovie("1", movie1);
    }

    @Test
    void testDeleteMovie() {
        doNothing().when(movieService).deleteMovie("1");

        ResponseEntity<Void> response = movieController.deleteMovie("1");

        assertThat(response.getStatusCodeValue()).isEqualTo(204);
        verify(movieService, times(1)).deleteMovie("1");
    }
}
