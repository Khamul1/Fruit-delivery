package com.example.fruitdelivery.dto;

import java.time.LocalDate;

public class FruitPriceDto {
    // Идентификатор записи о цене.
    private Long id;

    // Идентификатор фрукта.
    private Long fruitId;

    // Дата начала действия цены.
    private LocalDate startDate;

    // Дата окончания действия цены.
    private LocalDate endDate;

    // Цена фрукта.
    private Double price;

    // Идентификатор поставщика.
    private Long supplierId;

    // Пустой конструктор.
    public FruitPriceDto() {
    }

    // Конструктор с параметрами.
    public FruitPriceDto(Long id, LocalDate startDate, LocalDate endDate, Double price) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getFruitId() {
        return fruitId;
    }

    public void setFruitId(Long fruitId) {
        this.fruitId = fruitId;
    }
}
