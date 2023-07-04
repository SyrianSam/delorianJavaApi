package com.syriansam.delorianapi.delorianservice;

import com.syriansam.delorianapi.delorianEntities.Movie;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DelorianServiceTests {

    @Test
    public void testProcessMovies() {
        // Arrange
        List<String> content = Arrays.asList(
                "Back to the Future 1",
                "Movie 1",
                "Movie 2",
                "Back to the Future 1",
                "Movie 3",
                "Movie 4",
                "Movie 5"
        );
        double expectedTotalPrice = 130.0;

        // Act
        double totalPrice = DelorianService.processMovies(content);

        // Assert
        Assertions.assertEquals(expectedTotalPrice, totalPrice);
    }

    @Test
    public void testCalculateMoviePrices() {
        // Arrange
        List<Movie> movies = Arrays.asList(
                new Movie("Back to the Future 1", Movie.b2fPrice),
                new Movie("Movie 1", Movie.otherPrice),
                new Movie("Movie 2", Movie.otherPrice),
                new Movie("Back to the Future 2", Movie.b2fPrice),
                new Movie("Movie 3", Movie.otherPrice),
                new Movie("Movie 4", Movie.otherPrice),
                new Movie("Movie 5", Movie.otherPrice)
        );
        int seriesCount = 2;
        double expectedTotalPrice = 127.0;

        // Act
        double totalPrice = DelorianService.calculateMoviePrices(movies, seriesCount);

        // Assert
        Assertions.assertEquals(expectedTotalPrice, totalPrice);
    }

    @Test
    public void testConvertToArrayList() {
        // Arrange
        Movie movie1 = new Movie("Movie 1", Movie.otherPrice);
        Movie movie2 = new Movie("Movie 2", Movie.otherPrice);
        Map<Integer, Movie> moviesMap = new HashMap<>();
        moviesMap.put(1, movie1);
        moviesMap.put(2, movie2);

        // Act
        List<Movie> moviesList = DelorianService.convertToArrayList(moviesMap);

        // Assert
        Assertions.assertEquals(2, moviesList.size());
        Assertions.assertTrue(moviesList.contains(movie1));
        Assertions.assertTrue(moviesList.contains(movie2));
    }
}
