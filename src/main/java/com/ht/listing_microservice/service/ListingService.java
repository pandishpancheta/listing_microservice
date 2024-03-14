package com.ht.listing_microservice.service;

import com.ht.listing_microservice.dto.Status;
import com.ht.listing_microservice.entity.Listing;
import com.ht.listing_microservice.entity.ListingTag;
import com.ht.listing_microservice.entity.Tag;
import com.ht.listing_microservice.repository.ListingRepository;
import com.ht.listing_microservice.repository.ListingTagRepository;
import com.ht.listing_microservice.repository.TagRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ListingService {

    private final ListingRepository listingRepository;
    private final TagRepository tagRepository;
    private final ListingTagRepository listingTagRepository;

    public ListingService(ListingRepository listingRepository, TagRepository tagRepository, ListingTagRepository listingTagRepository) {
        this.listingRepository = listingRepository;
        this.tagRepository = tagRepository;
        this.listingTagRepository = listingTagRepository;
    }

    @Transactional(readOnly = true)
    public List<Listing> getAllListings() {
        return listingRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Listing getListing(Long id) {
        return listingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Listing with id --" + id + "-- does not exist"));
    }

    @Transactional
    public Listing createListing(Listing listing, List<String> tagNames) {
        // Save the listing to the database
        Listing savedListing = listingRepository.save(listing);

        // Handle the tags
        for (String tagName : tagNames) {
            // Check if the tag already exists
            Tag tag = tagRepository.findByName(tagName);
            if (tag == null) {
                // If the tag does not exist, create a new tag
                tag = new Tag();
                tag.setName(tagName);
                tag = tagRepository.save(tag);
            }

            // Create a connection between the listing and the tag
            ListingTag listingTag = new ListingTag();
            listingTag.setListing(savedListing);
            listingTag.setTag(tag);
            listingTagRepository.save(listingTag);
        }

        return savedListing;
    }

    public Listing updateListing(Long id, Listing newListing) {
        // Fetch the existing listing
        Listing existingListing = listingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Listing not found with id " + id));

        // Update the fields of the existing listing with the new values, but keep the status and tags unchanged if they are not provided
        existingListing.setName(newListing.getName() != null ? newListing.getName() : existingListing.getName());
        existingListing.setDescription(newListing.getDescription() != null ? newListing.getDescription() : existingListing.getDescription());
        existingListing.setWatermarkUri(newListing.getWatermarkUri() != null ? newListing.getWatermarkUri() : existingListing.getWatermarkUri());
        existingListing.setUri(newListing.getUri() != null ? newListing.getUri() : existingListing.getUri());
        existingListing.setPrice(newListing.getPrice() != null ? newListing.getPrice() : existingListing.getPrice());

        // Save the updated listing back to the database
        return listingRepository.save(existingListing);
    }

    @Transactional
    public Listing updateListingStatus(Long id, Status status) {
        Listing existingListing = listingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Listing with id " + id + " does not exist"));
        existingListing.setStatus(status);
        return listingRepository.save(existingListing);
    }

    @Transactional
    public void deleteListing(Long id) {
        listingRepository.deleteById(id);
    }
}