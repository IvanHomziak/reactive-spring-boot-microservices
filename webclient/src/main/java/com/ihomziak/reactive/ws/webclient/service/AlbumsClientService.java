package com.ihomziak.reactive.ws.webclient.service;

import com.ihomziak.reactive.ws.webclient.presentation.model.AlbumRest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface AlbumsClientService {

    Flux<AlbumRest> getAlbums(String jwt);

    Mono<AlbumRest> getAlbum(UUID id, String jwt);

    Mono<AlbumRest> createAlbum(Mono<AlbumRest> albumm, String jwt);

    Mono<AlbumRest> updateAlbum(UUID id, Mono<AlbumRest> album, String jwt);

    Mono<Void> deleteAlbum(UUID id, String jwt);
}
