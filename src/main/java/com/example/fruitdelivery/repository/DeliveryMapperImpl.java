package com.example.fruitdelivery.repository;

import com.example.fruitdelivery.dto.DeliveryDto;
import com.example.fruitdelivery.dto.DeliveryItemDto;
import com.example.fruitdelivery.model.Delivery;
import com.example.fruitdelivery.model.DeliveryItem;
import com.example.fruitdelivery.model.Fruit;
import com.example.fruitdelivery.model.Supplier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DeliveryMapperImpl implements DeliveryMapper {

    // Метод преобразует сущность Delivery в DTO DeliveryDto
    @Override
    public DeliveryDto toDto(Delivery delivery) {
        DeliveryDto deliveryDto = new DeliveryDto();
        // Проверяем, не равен ли null объект Supplier перед вызовом getId
        if (delivery.getSupplier() != null) {
            deliveryDto.setSupplierId(delivery.getSupplier().getId());
        } else {
            // Устанавливаем значение по умолчанию для SupplierId
            deliveryDto.setSupplierId(0L); // или выбросить исключение
        }
        deliveryDto.setDeliveryDate(delivery.getDeliveryDate());
        deliveryDto.setItems(mapItems(delivery.getItems()));
        return deliveryDto;
    }

    // Метод преобразует DTO DeliveryDto в сущность Delivery
    @Override
    public Delivery toEntity(DeliveryDto deliveryDto) {
        Delivery delivery = new Delivery();
        delivery.setSupplier(new Supplier(deliveryDto.getSupplierId()));
        delivery.setDeliveryDate(deliveryDto.getDeliveryDate());
        delivery.setItems(mapItemsToEntity(deliveryDto.getItems()));
        return delivery;
    }

    // Метод преобразует сущность DeliveryItem в DTO DeliveryItemDto
    @Override
    public DeliveryItem toEntity(DeliveryItemDto deliveryItemDto) {
        return null;
    }

    // Метод преобразует DTO DeliveryItemDto в сущность DeliveryItem
    @Override
    public DeliveryItemDto toDto(DeliveryItem deliveryItem) {
        return null;
    }

    // Вспомогательный метод, который преобразует список DeliveryItem в список DeliveryItemDto
    public List<DeliveryItemDto> mapItems(List<DeliveryItem> deliveryItems) {
        List<DeliveryItemDto> deliveryItemDtos = new ArrayList<>();
        for (DeliveryItem deliveryItem : deliveryItems) {
            DeliveryItemDto deliveryItemDto = new DeliveryItemDto();
            deliveryItemDto.setId(deliveryItem.getId());
            deliveryItemDto.setFruitId(deliveryItem.getFruit().getId());
            deliveryItemDto.setQuantity(deliveryItem.getQuantity());
            deliveryItemDtos.add(deliveryItemDto);
        }
        return deliveryItemDtos;
    }

    // Вспомогательный метод, который преобразует список DeliveryItemDto в список DeliveryItem
    public List<DeliveryItem> mapItemsToEntity(List<DeliveryItemDto> deliveryItemDtos) {
        List<DeliveryItem> deliveryItems = new ArrayList<>();
        for (DeliveryItemDto deliveryItemDto : deliveryItemDtos) {
            DeliveryItem deliveryItem = new DeliveryItem();
            deliveryItem.setId(deliveryItemDto.getId());
            deliveryItem.setFruit(new Fruit(deliveryItemDto.getFruitId()));
            deliveryItem.setQuantity(deliveryItemDto.getQuantity());
            deliveryItems.add(deliveryItem);
        }
        return deliveryItems;
    }
}
