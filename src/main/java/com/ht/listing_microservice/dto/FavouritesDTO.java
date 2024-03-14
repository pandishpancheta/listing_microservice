package com.ht.listing_microservice.dto;

import lombok.Data;

@Data
public class FavouritesDTO {
    private String id;
    private ListingDTO listing; // maybe listing id => String id
    //private User user; // ? not sure
}