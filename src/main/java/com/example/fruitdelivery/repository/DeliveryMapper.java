package com.example.fruitdelivery.repository;

import com.example.fruitdelivery.dto.DeliveryDto;
import com.example.fruitdelivery.model.Delivery;

public interface DeliveryMapper {

    DeliveryDto toDto(Delivery delivery);

    Delivery toEntity(DeliveryDto deliveryDto);
}
