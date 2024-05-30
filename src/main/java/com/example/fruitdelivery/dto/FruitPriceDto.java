package com.example.fruitdelivery.dto;

import java.time.LocalDate;

public class FruitPriceDto {
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double price;


    public FruitPriceDto() {
    }

    public FruitPriceDto(Long id, LocalDate startDate, LocalDate endDate, Double price) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
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

}
