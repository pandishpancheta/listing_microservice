package com.ht.listing_microservice.controller;

import com.ht.listing_microservice.dto.ListingDTO;
import com.ht.listing_microservice.dto.Status;
import com.ht.listing_microservice.entity.Listing;
import com.ht.listing_microservice.mapper.ListingMapper;
import com.ht.listing_microservice.service.ListingService;
import com.ht.listing_microservice.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/listings")
public class ListingController {

    private final ListingService listingService;
    private final ListingMapper listingMapper = ListingMapper.INSTANCE;

    private final TagService tagService;

    @GetMapping
    public List<ListingDTO> getAllListings() {
        List<Listing> listings = listingService.getAllListings();
        return listingMapper.toDTOs(listings);
    }

    @GetMapping("/{id}")
    public ListingDTO getListing(@PathVariable Long id) {
        Listing listing = listingService.getListing(id);
        return listingMapper.toDTO(listing);
    }

    @PostMapping
    public ResponseEntity<ListingDTO> createListing(@RequestBody ListingDTO listingDTO) {
        // Convert DTO to entity
        Listing listing = listingMapper.toEntity(listingDTO);
        // Create the listing
        Listing createdListing = listingService.createListing(listing, listingDTO.getTags());
        // Convert entity back to DTO
        ListingDTO createdListingDTO = listingMapper.toDTO(createdListing);
        // Return the created listing
        return ResponseEntity.ok(createdListingDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ListingDTO> updateListing(@PathVariable Long id, @RequestBody ListingDTO listingDTO) {
        // Convert DTO to entity
        Listing listing = listingMapper.toEntity(listingDTO);
        // Update the listing
        Listing updatedListing = listingService.updateListing(id, listing);
        // Convert entity back to DTO
        ListingDTO updatedListingDTO = listingMapper.toDTO(updatedListing);
        // Return the updated listing
        return ResponseEntity.ok(updatedListingDTO);
    }

    // Update the status of a listing for a given ID
    @PutMapping("/{id}/status")
    public ResponseEntity<ListingDTO> updateListingStatus(@PathVariable Long id, @RequestParam String status) {
        // Convert string status to enum
        Status enumStatus = Status.fromString(status);
        Listing updatedListing = listingService.updateListingStatus(id, enumStatus);
        return ResponseEntity.ok(listingMapper.toDTO(updatedListing));
    }


    @DeleteMapping("/{id}")
    public void deleteListing(@PathVariable Long id) {
        listingService.deleteListing(id);
    }
}
