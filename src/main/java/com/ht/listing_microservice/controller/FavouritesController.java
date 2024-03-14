package com.ht.listing_microservice.controller;

import com.ht.listing_microservice.dto.FavouritesDTO;
import com.ht.listing_microservice.mapper.FavouritesMapper;
import com.ht.listing_microservice.service.FavouritesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/favourites")
@RequiredArgsConstructor
@RestController
public class FavouritesController {
    private final FavouritesService favouritesService;

    private final FavouritesMapper favouritesMapper = FavouritesMapper.INSTANCE;

    @GetMapping
    public List<FavouritesDTO> getAllFavourites() {
        return favouritesMapper.toDTOs(favouritesService.getAllFavourites());
    }

    @GetMapping("/{id}")
    public FavouritesDTO getFavourites(@PathVariable Long id) {
        return favouritesMapper.toDTO(favouritesService.getFavourites(id).orElse(null));
    }

    @PostMapping
    public FavouritesDTO createFavourites(FavouritesDTO favouritesDTO) {
        System.out.println(favouritesDTO.getListingId());
        return favouritesMapper.toDTO(favouritesService.createFavourites(favouritesMapper.toEntity(favouritesDTO)));
    }

    @DeleteMapping("/{id}")
    public void deleteFavourites(@PathVariable Long id) {
        favouritesService.deleteFavourites(id);
    }
}
