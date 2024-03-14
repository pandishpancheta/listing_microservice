package com.ht.listing_microservice.controller;

import com.ht.listing_microservice.entity.Tag;
import com.ht.listing_microservice.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagController {

    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping("/listing/{listingId}")
    public ResponseEntity<Void> addTagToListing(@PathVariable Long listingId, @RequestBody String tagName) {
        tagService.addTagToListing(listingId, tagName);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/listing/{listingId}")
    public ResponseEntity<Void> deleteTagFromListing(@PathVariable Long listingId, @RequestBody String tagName) {
        tagService.deleteTagFromListing(listingId, tagName);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/listing/{listingId}")
    public ResponseEntity<List<Tag>> getAllTagsOfListing(@PathVariable Long listingId) {
        List<Tag> tags = tagService.getAllTagsOfListing(listingId);
        return ResponseEntity.ok(tags);
    }
}
