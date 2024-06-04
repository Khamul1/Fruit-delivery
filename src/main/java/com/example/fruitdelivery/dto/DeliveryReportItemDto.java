package com.example.fruitdelivery.dto;

import com.example.fruitdelivery.model.Fruit;

// DTO (Data Transfer Object) для представления информации о фрукте в отчете о доставке.
public class DeliveryReportItemDto {

    // Идентификатор фрукта.
    private Long fruitId;

    // Тип фрукта
    private String fruitType;

    // Количество фруктов.
    private int quantity;

    // Вес одного фрукта.
    private double weightPerUnit;

    // Цена одного фрукта.
    private double pricePerUnit;

    // Объект Fruit, содержащий информацию о фрукте.
    private Fruit fruit;

    // Общая стоимость фруктов.
    private double totalCost;

    // Общий вес фруктов.
    private double totalWeight;



    public Long getFruitId() {
        return fruitId;
    }

    public void setFruitId(Long fruitId) {
        this.fruitId = fruitId;
    }

    public String getFruitType() {
        return fruitType;
    }

    public void setFruitType(String fruitType) {
        this.fruitType = fruitType;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getWeightPerUnit() {
        return weightPerUnit;
    }

    public void setWeightPerUnit(double weightPerUnit) {
        this.weightPerUnit = weightPerUnit;
    }

    public double getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(double pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public Fruit getFruit() {
        return fruit;
    }

    public void setFruit(Fruit fruit) {
        this.fruit = fruit;
    }


    public double getWeight() {
        return quantity * weightPerUnit;
    }


    public double getCost() {
        return quantity * pricePerUnit;
    }

    public double getTotalCost() {
        return quantity * pricePerUnit;
    }

    public double getTotalWeight() {
        return quantity * weightPerUnit;
    }

    public void setTotalWeight(double totalWeight) {
        this.totalWeight = totalWeight;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }
}
