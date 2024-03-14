package com.ht.listing_microservice.repository;

import com.ht.listing_microservice.entity.Listing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ListingRepository extends JpaRepository<Listing, String> {
    List<Listing> findByName(String name);

    List<Listing> findByPriceBetween(double minPrice, double maxPrice);

    List<Listing> findByTagsContaining(String tag);

    List<Listing> findByDescriptionNotNull();

    List<Listing> findByOrderByPriceAsc();
}