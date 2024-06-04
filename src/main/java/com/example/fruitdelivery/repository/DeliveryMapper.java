package com.example.fruitdelivery.repository;

import com.example.fruitdelivery.dto.DeliveryDto;
import com.example.fruitdelivery.dto.DeliveryItemDto;
import com.example.fruitdelivery.model.Delivery;
import com.example.fruitdelivery.model.DeliveryItem;

import java.util.List;


public interface DeliveryMapper {

    // Преобразует сущность Delivery в DTO DeliveryDto.
    DeliveryDto toDto(Delivery delivery);

    // Преобразует DTO DeliveryDto в сущность Delivery.
    Delivery toEntity(DeliveryDto deliveryDto);

    // Преобразует DTO DeliveryItemDto в сущность DeliveryItem.
    DeliveryItem toEntity(DeliveryItemDto deliveryItemDto);

    // Преобразует сущность DeliveryItem в DTO DeliveryItemDto.
    DeliveryItemDto toDto(DeliveryItem deliveryItem);

    // Преобразует список DTO DeliveryItemDto в список сущностей DeliveryItem.
    List<DeliveryItem> mapItemsToEntity(List<DeliveryItemDto> deliveryItemDtos);

    // Преобразует список сущностей DeliveryItem в список DTO DeliveryItemDto.
    List<DeliveryItemDto> mapItems(List<DeliveryItem> deliveryItems);
}
