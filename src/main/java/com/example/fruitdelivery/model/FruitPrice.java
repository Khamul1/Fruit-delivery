package com.example.fruitdelivery.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class FruitPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fruit_id")
    private Fruit fruit;

    private double price;

    private LocalDate startDate;

    private LocalDate endDate;

    public FruitPrice() {

    }

    public FruitPrice(long id, Supplier supplier, Fruit fruit, double price, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.supplier = supplier;
        this.fruit = fruit;
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Fruit getFruit() {
        return fruit;
    }

    public void setFruit(Fruit fruit) {
        this.fruit = fruit;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDate getStartDate() {  // Исправлено
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {  // Исправлено
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
