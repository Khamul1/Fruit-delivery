package com.example.fruitdelivery.dto;

public class FruitDto {
    // Идентификатор фрукта.
    private Long id;

    // Тип фрукта
    private String type;

    // Сорт фрукта (например, "Гренни Смит", "Кавендиш").
    private String variety;

    // Количество фруктов.
    private int quantity;

    // Вес фрукта.
    private double weight;

    // Стоимость фрукта.
    private double cost;

    // Идентификатор поставщика.
    private Long supplierId;

    // Конструктор класса FruitDto.
    public FruitDto(Long id, String type, String variety, int quantity, double weight, double cost, Long supplierId) {
        this.id = id;
        this.type = type;
        this.variety = variety;
        this.quantity = quantity;
        this.weight = weight;
        this.cost = cost;
        this.supplierId = supplierId;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVariety() {
        return variety;
    }

    public void setVariety(String variety) {
        this.variety = variety;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}
