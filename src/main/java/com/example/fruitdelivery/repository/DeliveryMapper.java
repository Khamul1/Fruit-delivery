package com.example.fruitdelivery.repository;

import com.example.fruitdelivery.dto.DeliveryDto;
import com.example.fruitdelivery.dto.DeliveryItemDto;
import com.example.fruitdelivery.model.Delivery;
import com.example.fruitdelivery.model.DeliveryItem;

import java.util.List;

public interface DeliveryMapper {

    DeliveryDto toDto(Delivery delivery);

    Delivery toEntity(DeliveryDto deliveryDto);

    DeliveryItem toEntity(DeliveryItemDto deliveryItemDto);

    DeliveryItemDto toDto(DeliveryItem deliveryItem);

    List<DeliveryItem> mapItemsToEntity(List<DeliveryItemDto> deliveryItemDtos);

    List<DeliveryItemDto> mapItems(List<DeliveryItem> deliveryItems);
}
