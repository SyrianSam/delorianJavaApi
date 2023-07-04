package com.syriansam.delorianapi.deloriancontrollers;

import com.syriansam.delorianapi.delorianEntities.Movie;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.syriansam.delorianapi.delorianEntities.DelorianRequest;
import com.syriansam.delorianapi.delorianEntities.DelorianResponse;
import com.syriansam.delorianapi.delorianservice.DelorianService;

import java.util.ArrayList;
import java.util.List;

@RestController
@Log4j2
public class DelorianController {

    private final DelorianService delorianService;

    public DelorianController(DelorianService delorianService) {
        this.delorianService = delorianService;
    }

    @PostMapping(path = "/calculateTotal", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public DelorianResponse calculateTotal(@RequestBody DelorianRequest request) {

        double totalMovieCost;
        DelorianResponse response = new DelorianResponse();

        log.info("movieList: {}", request.getMovieList());

        if(request.getMovieList().isEmpty()){
            List<String> movieStringList = delorianService.populateMovieListFromFile();
            response.setTotal(delorianService.processMovies(movieStringList));
        }else{
            response.setTotal(delorianService.processMovies(request.getMovieList()));
        }


        return response;
    }
}
