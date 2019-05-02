package org.superbiz.moviefun.moviesapi;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestOperations;

import java.util.List;

import static org.springframework.http.HttpMethod.GET;

public class MoviesClient {

    private final String moviesUrl;
    private final RestOperations restOperations;

    private static ParameterizedTypeReference<List<MovieInfo>> movieListType = new ParameterizedTypeReference<List<MovieInfo>>() {
    };

    public MoviesClient(String moviesUrl, RestOperations restOperations) {
        this.moviesUrl = moviesUrl;
        this.restOperations = restOperations;
    }

    public void addMovie(MovieInfo movieInfo) {
        System.out.println("MOVIES SERVICE: " + movieInfo);
        HttpEntity<MovieInfo> httpEntity = new HttpEntity<>(movieInfo);
        restOperations.postForObject(moviesUrl, httpEntity, MovieInfo.class);
    }

    public void UpdateMovie(MovieInfo movieInfo) {
        HttpEntity<MovieInfo> httpEntity = new HttpEntity<>(movieInfo);
        restOperations.put(moviesUrl,httpEntity);
    }

    public void deleteMovie(MovieInfo movieInfo) {
        HttpEntity<MovieInfo> httpEntity = new HttpEntity<>(movieInfo);
        restOperations.delete(moviesUrl,httpEntity);
    }

    public void deleteMovieId(long id) {
        restOperations.delete(String.format("%s/%d",moviesUrl , id));
    }

    public List<MovieInfo> getMovies() {
       return restOperations.exchange(moviesUrl, GET, null, movieListType).getBody();
    }

    public List<MovieInfo> findAll(int firstResult, int maxResults) {
        String formattedUrl = String.format("%s?first_result=%d&max_results=%d", moviesUrl, firstResult, maxResults);
        return restOperations.exchange(formattedUrl, GET, null, movieListType).getBody();
    }

    public int countAll() {
        String formattedUrl = String.format("%s/count", moviesUrl);
        return restOperations.getForObject(formattedUrl, int.class);
    }

    public int count(String field, String searchTerm) {
        String formattedUrl = String.format("%s/count?field=%s&search_term=%s", moviesUrl, field, searchTerm);
        return restOperations.getForObject(formattedUrl, int.class);
    }

    public List<MovieInfo> findRange(String field, String searchTerm, int firstResult, int maxResults) {
        String formattedUrl = String.format("%s?field=%s&search_term=%s&first_result=%d&max_result=%d", moviesUrl, field, searchTerm,firstResult,maxResults);
        return restOperations.exchange(formattedUrl, GET, null, movieListType).getBody();
    }

    public void clean() {
        restOperations.delete(moviesUrl);
    }

}
