package com.ht.listing_microservice.controller;

import com.ht.listing_microservice.dto.FavouritesDTO;
import com.ht.listing_microservice.mapper.FavouritesMapper;
import com.ht.listing_microservice.service.FavouritesService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/favourites")
@RestController
public class FavouritesController {
    private final FavouritesService favouritesService;

    private final FavouritesMapper favouritesMapper;

    public FavouritesController(FavouritesService favouritesService, FavouritesMapper favouritesMapper) {
        this.favouritesService = favouritesService;
        this.favouritesMapper = favouritesMapper;
    }

    @GetMapping
    public List<FavouritesDTO> getAllFavourites() {
        return favouritesMapper.toDTOs(favouritesService.getAllFavourites());
    }

    @GetMapping("/{id}")
    public FavouritesDTO getFavourites(String id) {
        return favouritesMapper.toDTO(favouritesService.getFavourites(id).orElse(null));
    }

    @PostMapping
    public FavouritesDTO createFavourites(FavouritesDTO favouritesDTO) {
        return favouritesMapper.toDTO(favouritesService.createFavourites(favouritesMapper.toEntity(favouritesDTO)));
    }

    @DeleteMapping("/{id}")
    public void deleteFavourites(String id) {
        favouritesService.deleteFavourites(id);
    }
}
