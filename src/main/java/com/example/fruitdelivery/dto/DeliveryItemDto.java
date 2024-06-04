package com.example.fruitdelivery.dto;

import com.example.fruitdelivery.model.FruitPrice;

public class DeliveryItemDto {

    // ID товара в поставке
    private Long id;

    // ID фрукта, который входит в этот товар
    private Long fruitId;

    // Количество фрукта в кг
    private int quantity;

    // Цена за кг фрукта
    private double price;

    // Информация о цене фрукта
    private FruitPrice fruitPrice;


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


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


    public double getPrice() {
        return price;
    }


    public void setPrice(double price) {
        this.price = price;
    }


    public FruitPrice getFruitPrice() {
        return fruitPrice;
    }


    public void setFruitPrice(FruitPrice fruitPrice) {
        this.fruitPrice = fruitPrice;
    }


    public double getCost() {
        return quantity * fruitPrice.getPrice();
    }
}
