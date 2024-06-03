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

    @Override
    public DeliveryDto toDto(Delivery delivery) {
        DeliveryDto deliveryDto = new DeliveryDto();
        deliveryDto.setSupplierId(delivery.getSupplier().getId());
        deliveryDto.setDeliveryDate(delivery.getDeliveryDate());
        deliveryDto.setItems(mapItems(delivery.getItems()));
        return deliveryDto;
    }

    @Override
    public Delivery toEntity(DeliveryDto deliveryDto) {
        Delivery delivery = new Delivery();
        delivery.setSupplier(new Supplier(deliveryDto.getSupplierId()));
        delivery.setDeliveryDate(deliveryDto.getDeliveryDate());
        delivery.setItems(mapItemsToEntity(deliveryDto.getItems()));
        return delivery;
    }

    private List<DeliveryItemDto> mapItems(List<DeliveryItem> deliveryItems) {
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

    private List<DeliveryItem> mapItemsToEntity(List<DeliveryItemDto> deliveryItemDtos) {
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
