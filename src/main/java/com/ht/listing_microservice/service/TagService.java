package com.ht.listing_microservice.service;

import com.ht.listing_microservice.entity.Tag;

import java.util.List;

public interface TagService {
    void addTagToListing(Long listingId, String tagName);
    void deleteTagFromListing(Long listingId, String tagName);
    List<Tag> getAllTagsOfListing(Long listingId);
}
