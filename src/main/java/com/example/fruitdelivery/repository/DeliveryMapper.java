package com.example.fruitdelivery.repository;

import com.example.fruitdelivery.dto.DeliveryDto;
import com.example.fruitdelivery.model.Delivery;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DeliveryMapper {

    DeliveryDto toDto(Delivery delivery);
}
