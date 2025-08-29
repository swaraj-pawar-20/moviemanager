package com.movies.moviemanager.it;

import com.movies.moviemanager.entity.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MovieApiIT {

    @Container
    static MongoDBContainer mongo = new MongoDBContainer("mongo:6.0");

    @LocalServerPort
    int port;

    TestRestTemplate rest = new TestRestTemplate();

    @BeforeEach
    void setup() {
        // point Spring to testcontainer mongo
        String uri = mongo.getReplicaSetUrl() + "moviedb";
        System.setProperty("spring.data.mongodb.uri", uri);
    }

    @Test
    void createAndGetMovie() {
        Movie payload = new Movie(null,"Interstellar","Nolan",2014,"Sci-Fi",9);
        ResponseEntity<Movie> resp = rest.postForEntity(url("/movies"), payload, Movie.class);
        assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.OK);
        Movie created = resp.getBody();
        assertThat(created).isNotNull();
        Movie fetched = rest.getForObject(url("/movies/" + created.getId()), Movie.class);
        assertThat(fetched.getTitle()).isEqualTo("Interstellar");
    }

    private String url(String path) {
        return "http://localhost:" + port + path;
    }
}
