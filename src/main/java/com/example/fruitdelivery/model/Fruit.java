package com.example.fruitdelivery.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

@Entity
@Validated
public class Fruit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Тип фрукта не может быть пустым")
    private String type;

    @NotBlank(message = "Сорт фрукта не может быть пустым")
    private String variety;

    @Min(value = 1, message = "Количество фруктов должно быть не менее 1")
    private int quantity;

    @Positive(message = "Вес фруктов должен быть положительным числом")
    private double weight;

    @Positive(message = "Стоимость фруктов должна быть положительным числом")
    private double cost;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "supplier_id", nullable = false)
    private Supplier supplier;

    @OneToMany(mappedBy = "fruit", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FruitPrice> fruitPrices = new ArrayList<>();

    public Fruit() {
    }

    public Fruit(String type, String variety, int quantity, double weight, double cost, Supplier supplier) {
        this.type = type;
        this.variety = variety;
        this.quantity = quantity;
        this.weight = weight;
        this.cost = cost;
        this.supplier = supplier;
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

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public List<FruitPrice> getFruitPrices() {
        return fruitPrices;
    }

    public void setFruitPrices(List<FruitPrice> fruitPrices) {
        this.fruitPrices = fruitPrices;
    }

    public Long getSupplierId() {
        return supplier.getId();
    }
    public void setSupplierId(Long supplierId) {
        this.supplier.setId(supplierId);
    }
}
