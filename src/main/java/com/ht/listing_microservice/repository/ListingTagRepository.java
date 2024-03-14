package com.ht.listing_microservice.repository;

import com.ht.listing_microservice.entity.ListingTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListingTagRepository extends JpaRepository<ListingTag, Long> {
}