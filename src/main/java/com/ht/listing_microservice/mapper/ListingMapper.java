package com.ht.listing_microservice.mapper;

import com.ht.listing_microservice.dto.ListingDTO;
import com.ht.listing_microservice.entity.Listing;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ListingMapper {
    ListingMapper INSTANCE = Mappers.getMapper(ListingMapper.class);

    @Mappings({
            @Mapping(source = "status", target = "status")
    })
    ListingDTO toDTO(Listing listing);

    @Mappings({
            @Mapping(source = "status", target = "status")
    })
    Listing toEntity(ListingDTO listingDTO);


    List<ListingDTO> toDTOs(List<Listing> listings);
}