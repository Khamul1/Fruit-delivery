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

    public static class DeliveryItemDto {
        private Long fruitId;
        private int quantity;

        public Long getFruitId() {
            return fruitId;
        }

        public void setFruitId(Long fruitId) {
            this.fruitId = fruitId;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }

}
