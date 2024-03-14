package com.ht.listing_microservice.mapper;

import com.ht.listing_microservice.dto.FavouritesDTO;
import com.ht.listing_microservice.entity.Favourites;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface FavouritesMapper {

    FavouritesMapper INSTANCE = Mappers.getMapper(FavouritesMapper.class);

    @Mapping(source = "id", target = "listingId")
    FavouritesDTO toDTO(Favourites favourites);

    @Mapping(source = "listingId", target = "id")
    Favourites toEntity(FavouritesDTO favouritesDTO);

    List<FavouritesDTO> toDTOs(List<Favourites> favourites);
}