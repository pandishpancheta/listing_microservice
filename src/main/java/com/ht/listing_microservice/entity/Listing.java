package com.ht.listing_microservice.entity;

import java.util.UUID;

import com.ht.listing_microservice.dto.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "listing")
public class Listing {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String description;
    private String watermarkUri;
    private String uri;
    private Float price;
    @Enumerated(EnumType.STRING)
    private Status status;
}

