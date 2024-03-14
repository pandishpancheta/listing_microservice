package com.ht.listing_microservice.repository;

import com.ht.listing_microservice.entity.Listing;
import com.ht.listing_microservice.entity.ListingTag;
import com.ht.listing_microservice.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ListingTagRepository extends JpaRepository<ListingTag, Long> {
    ListingTag findByListingIdAndTagId(Listing listing_id, Tag tag_id);
    List<ListingTag> findByListingId(Listing listing_id);
}