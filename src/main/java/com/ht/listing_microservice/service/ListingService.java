package com.ht.listing_microservice.service;

import com.ht.listing_microservice.dto.Status;
import com.ht.listing_microservice.entity.Listing;

import java.util.List;

public interface ListingService {
    List<Listing> getAllListings();
    Listing getListing(Long id);
    Listing createListing(Listing listing, List<String> tagNames);
    Listing updateListing(Long id, Listing newListing);
    Listing updateListingStatus(Long id, Status status);
    void deleteListing(Long id);
}
