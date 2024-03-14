package com.ht.listing_microservice.service.impl;

import com.ht.listing_microservice.entity.Favourites;
import com.ht.listing_microservice.repository.FavouritesRepository;
import com.ht.listing_microservice.service.FavouritesService;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Data
public class FavouritesServiceImpl implements FavouritesService {
    private final FavouritesRepository favouritesRepository;

    @Override
    public List<Favourites> getAllFavourites() {
        return favouritesRepository.findAll();
    }

    @Override
    public Optional<Favourites> getFavourites(Long id) {
        return favouritesRepository.findById(id);
    }

    @Override
    public Favourites createFavourites(Favourites favourites) {
        // Check if the associated Listing is already persisted
        if (favourites.getListing() != null && favourites.getListing().getId() == null) {
            throw new IllegalArgumentException("The associated Listing must be persisted before saving Favourites.");
        }
        // Save the Favourites entity
        return favouritesRepository.save(favourites);
    }

    @Override
    public void deleteFavourites(Long id) {
        favouritesRepository.deleteById(id);
    }
}
