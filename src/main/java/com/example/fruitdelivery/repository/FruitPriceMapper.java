package com.example.fruitdelivery.repository;

import com.example.fruitdelivery.dto.FruitPriceDto;
import com.example.fruitdelivery.model.FruitPrice;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FruitPriceMapper {

    @Mapping(target = "fruitId", source = "fruit.id")
    FruitPriceDto toDto(FruitPrice fruitPrice);

    @Mapping(target = "fruit.id", source = "fruitId")
    FruitPrice toEntity(FruitPriceDto fruitPriceDto);
}
