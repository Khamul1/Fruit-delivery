package com.example.fruitdelivery.dto;

import com.example.fruitdelivery.model.Fruit;

public class DeliveryReportItem {
    private Fruit fruit;
    private int quantity;
    private double pricePerUnit; // Цена за единицу товара
    private double weightPerUnit; // Вес за единицу товара
    private double totalCost; // Общая стоимость
    private double totalWeight; // Общий весВес за единицу товара

    public Fruit getFruit() {
        return fruit;
    }

    public void setFruit(Fruit fruit) {
        this.fruit = fruit;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(double pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public double getWeightPerUnit() {
        return weightPerUnit;
    }

    public void setWeightPerUnit(double weightPerUnit) {
        this.weightPerUnit = weightPerUnit;
    }

    public double getTotalCost() {
        return quantity * pricePerUnit;
    }

    public double getTotalWeight() {
        return quantity * weightPerUnit;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public void setTotalWeight(double totalWeight) {
        this.totalWeight = totalWeight;
    }
}
