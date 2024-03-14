package com.ht.listing_microservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListingDTO {
    private UUID id;
    private String name;
    private String description;
    private String watermarkUri;
    private String uri;
    private Float price; // ? not sure big decimal!!!
    private String status;
    private List<String> tags;
}