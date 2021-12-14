package com.shyrski.profit.tracker.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.shyrski.profit.tracker.model.db.Collection;
import com.shyrski.profit.tracker.model.db.Nft;
import com.shyrski.profit.tracker.model.dto.CollectionDto;

@Mapper(componentModel = "spring")
public interface CollectionMapper {
    @Mapping(target = "items", source = "nfts", qualifiedByName = "generateItems")
    CollectionDto toDto(Collection collection);

    List<CollectionDto> toDtoList(List<Collection> collection);

    @Named("generateItems")
    default Long generateItems(List<Nft> nfts) {
        return (long) nfts.size();
    }
}
