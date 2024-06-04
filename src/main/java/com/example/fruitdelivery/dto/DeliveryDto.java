package com.example.fruitdelivery.dto;

import java.time.LocalDate;
import java.util.List;

public class DeliveryDto {
    // ID поставщика, связанного с этой поставкой
    private Long supplierId;

    // Дата поставки
    private LocalDate deliveryDate;

    // Список товаров, включенных в поставку
    private List<DeliveryItemDto> items;

    // Общая стоимость поставки
    private double totalCost;


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
        return totalCost;
    }


    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }
}
