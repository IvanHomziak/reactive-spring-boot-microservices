package com.ihomziak.reactive.ws.webclient.presentation;

import com.ihomziak.reactive.ws.webclient.presentation.model.AlbumRest;
import com.ihomziak.reactive.ws.webclient.service.AlbumsClientServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/albums-client/albums")
public class AlbumsClientController {
    private final AlbumsClientServiceImpl albumsClientService;

    public AlbumsClientController(AlbumsClientServiceImpl albumsClientService) {
        this.albumsClientService = albumsClientService;
    }

    @GetMapping
    public Flux<AlbumRest> getAlbums(@RequestHeader(name = "Authorization") String jwt) {
        return albumsClientService.getAlbums(jwt);
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<AlbumRest>> getAlbum(@PathVariable UUID id,
            @RequestHeader(name = "Authorization") String jwt) {
        return albumsClientService.getAlbum(id, jwt)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<AlbumRest>> createAlbum(@Valid @RequestBody Mono<AlbumRest> album,
            @RequestHeader(name = "Authorization") String jwt) {
        return albumsClientService.createAlbum(album, jwt)
                .map(albumRest -> ResponseEntity
                        .status(HttpStatus.CREATED)
                        .location(URI.create("/albums-client/" + albumRest.getId()))
                        .body(albumRest));
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<AlbumRest>> updateAlbum(@PathVariable UUID id,
            @Valid @RequestBody Mono<AlbumRest> album,
            @RequestHeader(name = "Authorization") String jwt) {
        return albumsClientService.updateAlbum(id, album, jwt)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteAlbum(@PathVariable UUID id,
            @RequestHeader(name = "Authorization") String jwt) {
        return albumsClientService.deleteAlbum(id, jwt)
                .then(Mono.just(ResponseEntity.noContent().<Void>build()))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}