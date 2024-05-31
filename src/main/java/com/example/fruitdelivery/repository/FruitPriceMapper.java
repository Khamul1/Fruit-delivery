package com.example.fruitdelivery.repository;

import com.example.fruitdelivery.dto.FruitPriceDto;
import com.example.fruitdelivery.model.FruitPrice;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring") // Укажите componentModel
public interface FruitPriceMapper {

    FruitPriceMapper INSTANCE = Mappers.getMapper(FruitPriceMapper.class);

    FruitPriceDto toDto(FruitPrice fruitPrice);

    FruitPrice toEntity(FruitPriceDto fruitPriceDto);
}
