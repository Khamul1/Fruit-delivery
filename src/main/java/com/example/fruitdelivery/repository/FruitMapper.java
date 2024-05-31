package com.example.fruitdelivery.repository;

import com.example.fruitdelivery.dto.FruitDto;
import com.example.fruitdelivery.model.Fruit;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FruitMapper {

    FruitDto toDto(Fruit fruit);

    Fruit toEntity(FruitDto fruitDto);
}
