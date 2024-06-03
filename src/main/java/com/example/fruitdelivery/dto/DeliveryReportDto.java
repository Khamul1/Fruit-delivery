package com.example.fruitdelivery.dto;

import java.util.List;

import com.example.fruitdelivery.dto.DeliveryReportItemDto;

public class DeliveryReportDto {

    private Long supplierId;
    private List<DeliveryReportItemDto> items;

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public List<DeliveryReportItemDto> getItems() {
        return items;
    }

    public void setItems(List<DeliveryReportItemDto> items) {
        this.items = items;
    }
    public double getTotalWeight() {
        return items.stream()
                .mapToDouble(DeliveryReportItemDto::getWeight)
                .sum();
    }

    public double getTotalCost() {
        return items.stream()
                .mapToDouble(DeliveryReportItemDto::getCost)
                .sum();
    }


}
