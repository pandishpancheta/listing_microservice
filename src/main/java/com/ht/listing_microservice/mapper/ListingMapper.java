package com.ht.listing_microservice.mapper;

import com.ht.listing_microservice.dto.ListingDTO;
import com.ht.listing_microservice.entity.Listing;
import com.ht.listing_microservice.entity.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface ListingMapper {

    ListingMapper INSTANCE = Mappers.getMapper(ListingMapper.class);

    @Mapping(source = "tags", target = "tags", qualifiedByName = "tagsToStringArray")
    ListingDTO toDTO(Listing listing);

    @Mapping(source = "tags", target = "tags", qualifiedByName = "stringArrayToTags")
    Listing toEntity(ListingDTO listingDTO);

    @Mapping(source = "tags", target = "tags", qualifiedByName = "tagsToStringArray")
    List<ListingDTO> toDTOs(List<Listing> listings);

    default String[] tagsToStringArray(List<Tag> tags) {
        return tags.stream().map(Tag::getName).toArray(String[]::new);
    }

    default List<Tag> stringArrayToTags(String[] tags) {
        return tags == null ? null : Arrays.stream(tags).map(tag -> {
            Tag t = new Tag();
            t.setName(tag);
            return t;
        }).collect(Collectors.toList());
    }
}
