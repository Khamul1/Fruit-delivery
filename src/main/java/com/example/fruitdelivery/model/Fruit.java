package com.example.fruitdelivery.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.springframework.validation.annotation.Validated;

@Entity
@Validated
public class Fruit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    @JsonManagedReference
    private Supplier supplier;

    public Fruit(Long id, String type, String variety, int quantity, double weight, double cost, Supplier supplier) {
        this.id = id;
        this.type = type;
        this.variety = variety;
        this.quantity = quantity;
        this.weight = weight;
        this.cost = cost;
        this.supplier = supplier;
    }

    @NotBlank(message = "Тип фрукта не может быть пустым") // Используем аннотацию @NotBlank из Spring Validator
    private String type;

    @NotBlank(message = "Сорт фрукта не может быть пустым") // Используем аннотацию @NotBlank из Spring Validator
    private String variety;

    @Min(value = 1, message = "Количество фруктов должно быть не менее 1") // Используем аннотацию @Min из Spring Validator
    private int quantity;

    @Positive(message = "Вес фруктов должен быть положительным числом") // Используем аннотацию @Positive из Spring Validator
    private double weight;

    @Positive(message = "Стоимость фруктов должна быть положительным числом") // Используем аннотацию @Positive из Spring Validator
    private double cost;


    public Fruit() {

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
}
