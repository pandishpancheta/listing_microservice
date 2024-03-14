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
    public Optional<Favourites> getFavourites(Long id) {
        return favouritesRepository.findById(id);
    }

    @Transactional
    public Favourites createFavourites(Favourites favourites) {
        // Check if the associated Listing is already persisted
        if (favourites.getListing() != null && favourites.getListing().getId() == null) {
            throw new IllegalArgumentException("The associated Listing must be persisted before saving Favourites.");
        }
        // Save the Favourites entity
        return favouritesRepository.save(favourites);
    }


    @Transactional
    public void deleteFavourites(Long id) {
        favouritesRepository.deleteById(id);
    }
}
