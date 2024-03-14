package com.ht.listing_microservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FavouritesDTO {
    private UUID id;
    private UUID listingId; // maybe listing id => String id
    //private User user; // ? not sure
}