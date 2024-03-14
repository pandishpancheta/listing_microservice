package com.ht.listing_microservice.dto;

import lombok.Data;

import java.util.List;

@Data
public class ListingDTO {
    private Long id;
    private String name;
    private String description;
    private String watermarkUri;
    private String uri;
    private Float price; // ? not sure big decimal!!!
    private String status;
    private List<String> tags;

    // Getter and Setter for status
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}