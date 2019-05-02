package org.superbiz.moviefun.albums;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/albums")
public class AlbumsController {

    private final AlbumsBean albumsBean;

    public AlbumsController(AlbumsBean albumsBean) {
        this.albumsBean = albumsBean;
    }

    @PostMapping
    public void addAlbum(@RequestBody Album album){
        albumsBean.addAlbum(album);
    }

    @GetMapping
    public List<Album> getAlbums(){
        return  albumsBean.getAlbums();
    }

    @GetMapping("/{id}")
    public Album getAlbum(@PathVariable long id){
        return albumsBean.find(id);
    }


}
