package org.superbiz.moviefun.movies;

import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MoviesController {

    private final MoviesRepository moviesRepository;

    public MoviesController(MoviesRepository moviesRepository) {
        this.moviesRepository = moviesRepository;
    }


    @PostMapping
    public void createMovie(@RequestBody Movie movie) {
        System.out.println("Movie CONTROLLER:" + movie);
        moviesRepository.addMovie(movie);
    }

    @PutMapping
    public void updateMovie(@RequestBody Movie movie) {
        moviesRepository.updateMovie(movie);
    }

    @DeleteMapping
    public void deleteMovie(@RequestBody Movie movie) {
        moviesRepository.deleteMovie(movie);
    }

    @DeleteMapping("/{id}")
    public void deleteMovieId (@PathVariable("id") long id){
        moviesRepository.deleteMovieId(id);
    }


    @GetMapping
    public List<Movie> find(@RequestParam(value = "field",required = false) String field,
                                 @RequestParam(value = "search_term", required = false) String searchTerm,
                                 @RequestParam(value = "first_result",required = false) Integer firstResult,
                                 @RequestParam(value = "max_result",required = false) Integer maxResults){

        if (field != null && searchTerm != null){
            return moviesRepository.findRange(field,searchTerm,firstResult,maxResults);
        }else if (firstResult != null && maxResults != null ){
            return moviesRepository.findAll(firstResult, maxResults);
        }else{
            return moviesRepository.getMovies();
        }
    }


    @GetMapping("/count")
    public int count(@RequestParam(value = "field",required = false) String field,
                     @RequestParam(value = "search_term",required = false) String searchTerm)
            throws MissingServletRequestParameterException {
        if (field ==null && searchTerm == null){
            return moviesRepository.countAll();
        }else if (field != null && searchTerm != null){
            return moviesRepository.count(field,searchTerm);
        }else if (field == null){
            throw new MissingServletRequestParameterException("field", "String");
        } else {
            throw new MissingServletRequestParameterException("search_term", "String");
        }
    }

}
