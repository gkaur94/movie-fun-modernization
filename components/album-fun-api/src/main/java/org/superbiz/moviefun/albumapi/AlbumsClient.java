package org.superbiz.moviefun.albumapi;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestOperations;

import java.util.List;

import static org.springframework.http.HttpMethod.GET;

public class AlbumsClient {

    private final String albumsUrl;
    private final RestOperations restOperations;

    private static ParameterizedTypeReference<List<AlbumInfo>> albumListType = new ParameterizedTypeReference<List<AlbumInfo>>() {
    };

    public AlbumsClient(String albumsUrl, RestOperations restOperations) {
        this.albumsUrl = albumsUrl;
        this.restOperations = restOperations;
    }

    public void addAlbum(AlbumInfo album) {
        HttpEntity<AlbumInfo> httpEntity = new HttpEntity<>(album);
        restOperations.postForObject(albumsUrl,httpEntity,AlbumInfo.class);
    }

    public AlbumInfo find(long id) {
        String formattedURL = String.format("%s/%d", albumsUrl,id);
        return restOperations.getForObject(formattedURL,AlbumInfo.class);
    }

    public List<AlbumInfo> getAlbums() {
        return restOperations.exchange(albumsUrl, GET, null, albumListType).getBody();
    }

}