package com.ht.listing_microservice.mapper;

import com.ht.listing_microservice.dto.FavouritesDTO;
import com.ht.listing_microservice.entity.Favourites;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface FavouritesMapper {

    FavouritesMapper INSTANCE = Mappers.getMapper(FavouritesMapper.class);

    FavouritesDTO toDTO(Favourites favourites);

    List<FavouritesDTO> toDTOs(List<Favourites> favourites);

    Favourites toEntity(FavouritesDTO favouritesDTO);
}