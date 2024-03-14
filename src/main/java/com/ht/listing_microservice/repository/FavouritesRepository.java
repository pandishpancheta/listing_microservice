package com.ht.listing_microservice.repository;

import com.ht.listing_microservice.entity.Favourites;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavouritesRepository extends JpaRepository<Favourites, Long> { }
