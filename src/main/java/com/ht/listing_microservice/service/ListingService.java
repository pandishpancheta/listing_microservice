package com.ht.listing_microservice.service;

import com.ht.listing_microservice.entity.Listing;
import com.ht.listing_microservice.repository.ListingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ListingService {

    private final ListingRepository listingRepository;

    public ListingService(ListingRepository listingRepository) {
        this.listingRepository = listingRepository;
    }

    @Transactional(readOnly = true)
    public List<Listing> getAllListings() {
        return listingRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Listing getListing(String id) {
        return listingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Listing with id --" + id + "-- does not exist"));
    }

    @Transactional
    public Listing createListing(Listing listing) {
        return listingRepository.save(listing);
    }

    @Transactional
    public Listing updateListing(String id, Listing listing) {
        Listing existingListing = listingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Listing with id " + id + " does not exist"));
        existingListing.setName(listing.getName());
        existingListing.setDescription(listing.getDescription());
        existingListing.setWatermarkUri(listing.getWatermarkUri());
        existingListing.setUri(listing.getUri());
        existingListing.setPrice(listing.getPrice());
        existingListing.setTags(listing.getTags());
        return listingRepository.save(existingListing);
    }

    @Transactional
    public void deleteListing(String id) {
        listingRepository.deleteById(id);
    }
}