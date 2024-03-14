package com.ht.listing_microservice.dto;

import lombok.Data;

@Data
public class ListingDTO {
    private String id;
    private String name;
    private String description;
    private String watermarkUri;
    private String uri;
    private double price; // ? not sure
    private String[] tags;
}