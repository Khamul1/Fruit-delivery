package com.example.fruitdelivery.dto;

import com.example.fruitdelivery.model.Fruit;

public class DeliveryReportItemDto {

    private Long fruitId;
    private String fruitType;
    private int quantity;
    private double weightPerUnit;
    private double pricePerUnit;
    private Fruit fruit;



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
}
