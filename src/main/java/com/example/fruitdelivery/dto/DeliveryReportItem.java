package com.example.fruitdelivery.dto;

import com.example.fruitdelivery.model.Fruit;

public class DeliveryReportItem {
    // Фрукт, который входит в этот товар
    private Fruit fruit;

    // Количество фрукта
    private int quantity;

    // Цена за единицу товара (например, за кг)
    private double pricePerUnit;

    // Вес за единицу товара (например, за кг)
    private double weightPerUnit;

    // Общая стоимость товара
    private double totalCost;

    // Общий вес товара
    private double totalWeight;


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

    // Метод для расчета общей стоимости товара
    public double getTotalCost() {
        return quantity * pricePerUnit;
    }

    // Метод для расчета общего веса товара
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
