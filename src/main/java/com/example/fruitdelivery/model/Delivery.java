package com.example.fruitdelivery.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;


@Entity
public class Delivery {
    // Идентификатор доставки.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Поставщик, осуществивший доставку.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    // Дата доставки.
    @Column(name = "delivery_date")
    private LocalDate deliveryDate;

    // Список позиций доставки.
    @OneToMany(mappedBy = "delivery", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DeliveryItem> items;

    // Общая стоимость доставки.
    private double totalCost;


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


    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }


    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }


    public List<DeliveryItem> getItems() {
        return items;
    }


    public void setItems(List<DeliveryItem> items) {
        this.items = items;
    }


    public double getTotalCost() {
        return totalCost;
    }


    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }
}
