package com.example.fruitdelivery.repository;

import com.example.fruitdelivery.dto.FruitDto;
import com.example.fruitdelivery.model.Fruit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FruitMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "type", source = "type")
    @Mapping(target = "weight", source = "weight")
    @Mapping(target = "cost", source = "cost")
    FruitDto toDto(Fruit fruit);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "type", source = "type")
    @Mapping(target = "weight", source = "weight")
    @Mapping(target = "cost", source = "cost")
    Fruit toEntity(FruitDto fruitDto);
}
