package com.example.fruitdelivery.dto;

import java.time.LocalDate;
import java.util.List;

public class DeliveryDto {
    private Long supplierId;
    private LocalDate deliveryDate;
    private List<DeliveryItemDto> items;


    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public List<DeliveryItemDto> getItems() {
        return items;
    }

    public void setItems(List<DeliveryItemDto> items) {
        this.items = items;
    }

    public double getTotalCost() {
        double totalCost = 0;
        for (DeliveryItemDto item : items) {
            totalCost += item.getQuantity() * item.getPrice();
        }
        return totalCost;
    }

}
