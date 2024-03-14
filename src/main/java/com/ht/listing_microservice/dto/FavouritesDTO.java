package com.ht.listing_microservice.dto;

import lombok.Data;

@Data
public class FavouritesDTO {
    private Long id;
    private Long listingId; // maybe listing id => String id
    //private User user; // ? not sure
}