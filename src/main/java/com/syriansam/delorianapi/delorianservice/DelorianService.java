package com.syriansam.delorianapi.delorianservice;

import com.syriansam.delorianapi.delorianEntities.Movie;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


@Service
@Log4j2
public class DelorianService {

    public static double processMovies(List<String> content) {

        Map<Integer, Movie> movies = new HashMap<>();
        Map<String, Movie> distinctMovies = new HashMap<>();
        Map<String, Boolean> encounteredMovies = new HashMap<>();

        int moviesCounter = 0;
        int seriesCount = 0;

        for (String line : content) {
            if (line.toLowerCase().startsWith("back to the future")) {
                if (!encounteredMovies.containsKey(line)) {
                    encounteredMovies.put(line, true);
                    distinctMovies.put(line, new Movie(line, Movie.b2fPrice));
                    movies.put(moviesCounter, new Movie(line, Movie.b2fPrice));
                    seriesCount++;
                } else {
                    movies.put(moviesCounter, new Movie(line, Movie.b2fPrice));
                }
            } else {
                movies.put(moviesCounter, new Movie(line, Movie.otherPrice));
            }
            moviesCounter++;
            log.debug("seriesCount: {}", seriesCount);
        }

        log.debug("Encountered Movies:");
        for (Map.Entry<String, Boolean> entry : encounteredMovies.entrySet()) {
            String movie = entry.getKey().trim();
            boolean encountered = entry.getValue();
            log.debug("Movie: {}, part of series?: {}", movie, encountered);
        }

        return calculateMoviePrices(convertToArrayList(movies), seriesCount);
    }

    public static double calculateMoviePrices(List<Movie> movies,int seriesCount) {
        log.debug("Movies:");
        for (Movie movie : movies) {
            log.debug("Movie: {}, Price: {}", movie.getName(), movie.getPrice());
        }

        double priceReduction;
        switch (seriesCount) {
            case 1:
                priceReduction = 0.0;
                break;
            case 2:
                priceReduction = 0.10;
                break;
            default:
                priceReduction = 0.20;
                break;
        }

        double totalPrice = 0.0;
        for (Movie movie : movies) {
            log.debug("to lowercase: {}",movie.getName().toLowerCase());
            if (movie.getName().toLowerCase().startsWith("back to the future")) {
                double reducedPrice = movie.getPrice() * (1 - priceReduction);
                totalPrice += reducedPrice;
            } else {
                totalPrice += movie.getPrice();
            }
        }
        log.info("movie prices after reduction: {}", totalPrice);

        return totalPrice;
    }

    static List<Movie> convertToArrayList(Map<Integer, Movie> moviesMap) {
        List<Movie> moviesList = new ArrayList<>();
        for (Map.Entry<Integer, Movie> entry : moviesMap.entrySet()) {
            moviesList.add(entry.getValue());
        }
        return moviesList;
    }

    public static List<String> populateMovieListFromFile() {

        InputStream inputStream = DelorianService.class.getClassLoader().getResourceAsStream("import/purchase_order_001.csv");
        List<String> movies = new ArrayList<>();
        if (inputStream != null) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    log.debug(line);
                    if(!line.isEmpty())
                        movies.add(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            log.info("File not found.");
        }
        return movies;
    }

    public static List<String> populateMovieListFromMultipleFiles() {
        List<String> movies = new ArrayList<>();

        try {
            Path directory = Paths.get(ClassLoader.getSystemResource("import").toURI());

            try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(directory)) {
                for (Path filePath : directoryStream) {
                    if (Files.isRegularFile(filePath)) {
                        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
                            String line;
                            log.info("file name:{}", filePath);
                            while ((line = reader.readLine()) != null) {
                                if (!line.isEmpty()) {
                                    movies.add(line);
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return movies;
    }

}





