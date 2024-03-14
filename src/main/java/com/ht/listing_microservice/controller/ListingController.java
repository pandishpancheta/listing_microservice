package com.ht.listing_microservice.controller;

import com.ht.listing_microservice.dto.ListingDTO;
import com.ht.listing_microservice.service.ListingService;
import com.ht.listing_microservice.mapper.ListingMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/listings")
public class ListingController {

    private final ListingService listingService;
    private final ListingMapper listingMapper;

    public ListingController(ListingService listingService, ListingMapper listingMapper) {
        this.listingService = listingService;
        this.listingMapper = listingMapper;
    }

    @GetMapping
    public List<ListingDTO> getAllListings() {
        return listingMapper.toDTOs(listingService.getAllListings());
    }

    @GetMapping("/{id}")
    public ListingDTO getListing(@PathVariable String id) {
        return listingMapper.toDTO(listingService.getListing(id));
    }

    @PostMapping
    public ListingDTO createListing(@RequestBody ListingDTO listingDTO) {
        return listingMapper.toDTO(listingService.createListing(listingMapper.toEntity(listingDTO)));
    }

    @PutMapping("/{id}")
    public ListingDTO updateListing(@PathVariable String id, @RequestBody ListingDTO listingDTO) {
        return listingMapper.toDTO(listingService.updateListing(id, listingMapper.toEntity(listingDTO)));
    }

    @DeleteMapping("/{id}")
    public void deleteListing(@PathVariable String id) {
        listingService.deleteListing(id);
    }
}