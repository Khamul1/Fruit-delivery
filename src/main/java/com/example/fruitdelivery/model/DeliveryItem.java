package com.example.fruitdelivery.model;

import jakarta.persistence.*;


@Entity
public class DeliveryItem {

    // Идентификатор позиции доставки.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Доставка, к которой относится позиция.
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "delivery_id", nullable = false)
    private Delivery delivery;

    // Фрукт, который входит в позицию доставки.
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "fruit_id", nullable = false)
    private Fruit fruit;

    // Количество фруктов в позиции доставки.
    @Column(nullable = false)
    private int quantity;


    public DeliveryItem() {
    }


    public DeliveryItem(Delivery delivery, Fruit fruit, int quantity) {
        this.delivery = delivery;
        this.fruit = fruit;
        this.quantity = quantity;
    }


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public Delivery getDelivery() {
        return delivery;
    }


    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }


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
}
