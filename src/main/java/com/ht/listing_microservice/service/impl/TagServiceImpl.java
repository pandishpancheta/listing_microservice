package com.ht.listing_microservice.service.impl;

import com.ht.listing_microservice.entity.Listing;
import com.ht.listing_microservice.entity.ListingTag;
import com.ht.listing_microservice.entity.Tag;
import com.ht.listing_microservice.repository.ListingRepository;
import com.ht.listing_microservice.repository.ListingTagRepository;
import com.ht.listing_microservice.repository.TagRepository;
import com.ht.listing_microservice.service.TagService;
import jakarta.transaction.Transactional;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Data
public class TagServiceImpl implements TagService {
    private final ListingRepository listingRepository;
    private final TagRepository tagRepository;
    private final ListingTagRepository listingTagRepository;

    @Override
    public void addTagToListing(Long listingId, String tagName) {
        // Fetch the listing
        Listing listing = listingRepository.findById(listingId)
                .orElseThrow(() -> new IllegalArgumentException("Listing with id " + listingId + " does not exist"));

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
        listingTag.setListing(listing);
        listingTag.setTag(tag);
        listingTagRepository.save(listingTag);
    }

    @Override
    public void deleteTagFromListing(Long listingId, String tagName) {
        // Fetch the listing
        Listing listing = listingRepository.findById(listingId)
                .orElseThrow(() -> new IllegalArgumentException("Listing with id " + listingId + " does not exist"));

        // Fetch the tag
        Tag tag = tagRepository.findByName(tagName);
        if (tag == null) {
            throw new IllegalArgumentException("Tag with name " + tagName + " does not exist");
        }

        // Find the connection between the listing and the tag
        ListingTag listingTag = listingTagRepository.findByListingIdAndTagId(listing, tag);
        if (listingTag == null) {
            throw new IllegalArgumentException("Tag with name " + tagName + " is not associated with listing with id " + listingId);
        }

        // Delete the connection
        listingTagRepository.delete(listingTag);
    }

    @Override
    public List<Tag> getAllTagsOfListing(Long listingId) {
        // Fetch the listing
        Listing listing = listingRepository.findById(listingId)
                .orElseThrow(() -> new IllegalArgumentException("Listing with id " + listingId + " does not exist"));

        // Find all connections between the listing and tags
        List<ListingTag> listingTags = listingTagRepository.findByListingId(listing);

        // Extract the tags and return them
        return listingTags.stream()
                .map(ListingTag::getTag)
                .collect(Collectors.toList());
    }

}
