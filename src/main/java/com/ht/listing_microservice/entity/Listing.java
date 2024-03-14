package com.ht.listing_microservice.entity;

import java.util.List;
import java.util.UUID;

import com.ht.listing_microservice.dto.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Data
@AllArgsConstructor
@Table(name = "listing")
public class Listing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String watermarkUri;
    private String uri;
    private Float price;

    @Enumerated(EnumType.STRING)
    private Status status;


    // Generate random ID
    public Listing() {
        this.id = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
    }
}

