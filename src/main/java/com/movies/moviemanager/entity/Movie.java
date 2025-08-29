package com.movies.moviemanager.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.*;

import lombok.*;

@Document(collection = "movies")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movie {
    @Id
    private String id;

    @NotBlank(message = "Title is required")
    private String title;

    private String director;

    @Min(1880)
    private Integer releaseYear;

    private String genre;

    @Min(1) @Max(10)
    private Integer rating;
}
