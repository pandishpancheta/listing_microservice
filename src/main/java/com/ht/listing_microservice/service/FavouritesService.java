package com.ht.listing_microservice.service;

import com.ht.listing_microservice.entity.Favourites;
import com.ht.listing_microservice.repository.FavouritesRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class FavouritesService {

    private final FavouritesRepository favouritesRepository;

    public FavouritesService(FavouritesRepository favouritesRepository) {
        this.favouritesRepository = favouritesRepository;
    }

    @Transactional(readOnly = true)
    public List<Favourites> getAllFavourites() {
        return favouritesRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Favourites> getFavourites(String id) {
        return favouritesRepository.findById(id);
    }

    @Transactional
    public Favourites createFavourites(Favourites favourites) {
        return favouritesRepository.save(favourites);
    }

    @Transactional
    public void deleteFavourites(String id) {
        favouritesRepository.deleteById(id);
    }
}
