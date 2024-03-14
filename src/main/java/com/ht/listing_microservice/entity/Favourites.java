package com.ht.listing_microservice.entity;

import java.util.UUID;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Data
@AllArgsConstructor
@Table(name = "favourites")
public class Favourites {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @OneToOne
    private Listing listing;

    //@OneToOne
    //private User user;

    // Generate random ID
    public Favourites() {
        this.id = UUID.randomUUID().toString();
    }
}