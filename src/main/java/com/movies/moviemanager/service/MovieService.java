package com.movies.moviemanager.service;

import com.movies.moviemanager.dao.MovieRepository;
import com.movies.moviemanager.exception.MovieNotFoundException;
import com.movies.moviemanager.entity.Movie;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    private final MovieRepository repo;

    public MovieService(MovieRepository repo) {
        this.repo = repo;
    }

    //Pagination support
    public Page<Movie> getMovies(Pageable pageable) {
        return repo.findAll(pageable);
    }

    //Full list (if needed)
    public List<Movie> getAllMovies() {
        return repo.findAll();
    }

    //Get by ID
    public Movie getMovieById(String id) {
        return repo.findById(id)
                .orElseThrow(() -> new MovieNotFoundException("Movie not found with id: " + id));
    }

    //Create
    public Movie createMovie(@NotNull Movie movie) {
        movie.setId(null); // ensures a new ID is generated
        return repo.save(movie);
    }

    //Update
    public Movie updateMovie(String id, @NotNull Movie updated) {
        Movie movie = getMovieById(id);
        movie.setTitle(updated.getTitle());
        movie.setDirector(updated.getDirector());
        movie.setReleaseYear(updated.getReleaseYear());
        movie.setGenre(updated.getGenre());
        movie.setRating(updated.getRating());
        return repo.save(movie);
    }

    //Delete
    public void deleteMovie(String id) {
        if (!repo.existsById(id)) {
            throw new MovieNotFoundException("Movie not found with id: " + id);
        }
        repo.deleteById(id);
    }
}
