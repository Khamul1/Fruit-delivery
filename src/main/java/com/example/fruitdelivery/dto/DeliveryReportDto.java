package com.example.fruitdelivery.dto;

import java.util.List;

public class DeliveryReportDto {

    // ID поставщика, связанного с этим отчетом о поставке
    private Long supplierId;

    // Список товаров, включенных в отчет о поставке
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

    // Метод для расчета общего веса товаров в отчете
    public double getTotalWeight() {
        return items.stream()
                .mapToDouble(DeliveryReportItemDto::getWeight)
                .sum();
    }

    // Метод для расчета общей стоимости товаров в отчете
    public double getTotalCost() {
        return items.stream()
                .mapToDouble(DeliveryReportItemDto::getCost)
                .sum();
    }
}
