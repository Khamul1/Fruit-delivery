package com.example.fruitdelivery.model;

import jakarta.persistence.*;
import java.time.LocalDate;


@Entity
public class FruitPrice {

    // Идентификатор цены фрукта.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Поставщик фрукта.
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "supplier_id", nullable = false)
    private Supplier supplier;

    // Фрукт, для которого установлена цена.
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "fruit_id", nullable = false)
    private Fruit fruit;

    // Цена фрукта.
    @Column(nullable = false)
    private double price;

    // Дата начала действия цены.
    @Column(nullable = false)
    private LocalDate startDate;

    // Дата окончания действия цены.
    @Column(nullable = false)
    private LocalDate endDate;


    public FruitPrice() {
    }


    public FruitPrice(Long supplierId, Fruit fruit, double price, LocalDate startDate, LocalDate endDate) {
        this.supplier = supplier;
        this.fruit = fruit;
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
    }


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
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
}
