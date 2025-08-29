package com.movies.moviemanager.controller;

import com.movies.moviemanager.entity.Movie;
import com.movies.moviemanager.service.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;


import java.util.List;

@RestController
@RequestMapping("/movies")
@Tag(name = "Movie Management API", description = "CRUD operations for movies")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    //Paginated endpoint
    @GetMapping
    @Operation(summary = "List movies (paginated)")
    public Page<Movie> getMovies(@PageableDefault(size = 10, sort = "title") Pageable pageable) {
        return movieService.getMovies(pageable);
    }

    //Full list
    @GetMapping("/all")
    @Operation(summary = "List all movies")
    public List<Movie> getAllMovies() {
        return movieService.getAllMovies();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get movie by ID")
    public Movie getMovieById(@PathVariable String id) {
        return movieService.getMovieById(id);
    }

    @PostMapping
    @Operation(summary = "Create a new movie")
    public ResponseEntity<Movie> createMovie(@Valid @RequestBody Movie movie) {
        return ResponseEntity.ok(movieService.createMovie(movie));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing movie")
    public ResponseEntity<Movie> updateMovie(@PathVariable String id, @Valid @RequestBody Movie movie) {
        return ResponseEntity.ok(movieService.updateMovie(id, movie));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a movie by ID")
    public ResponseEntity<Void> deleteMovie(@PathVariable String id) {
        movieService.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }

}
