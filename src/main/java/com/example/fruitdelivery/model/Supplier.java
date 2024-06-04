package com.example.fruitdelivery.model;

import jakarta.persistence.*;


@Entity
public class Supplier {
    // Идентификатор поставщика.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Название поставщика.
    private String name;

    // Адрес поставщика.
    private String address;


    public Supplier() {
    }


    public Supplier(Long id) {
        this.id = id;
    }


    public Supplier(String name, String address) {
        this.name = name;
        this.address = address;
    }


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getAddress() {
        return address;
    }


    public void setAddress(String address) {
        this.address = address;
    }
}
