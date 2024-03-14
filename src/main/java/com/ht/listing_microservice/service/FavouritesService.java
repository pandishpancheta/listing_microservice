package com.ht.listing_microservice.service;

import com.ht.listing_microservice.entity.Favourites;

import java.util.List;
import java.util.Optional;

public interface FavouritesService {
    List<Favourites> getAllFavourites();
    Optional<Favourites> getFavourites(Long id);
    Favourites createFavourites(Favourites favourites);
    void deleteFavourites(Long id);
}
