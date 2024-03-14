package com.ht.listing_microservice.dto;

public enum Status {
    COMPLETED,
    PENDING,
    FAILED;

    public static Status fromString(String status) {
        for (Status s : Status.values()) {
            if (s.name().equalsIgnoreCase(status)) {
                return s;
            }
        }
        throw new IllegalArgumentException("Invalid status: " + status);
    }
}

