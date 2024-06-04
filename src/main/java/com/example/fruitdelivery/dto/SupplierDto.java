package com.example.fruitdelivery.dto;

import com.example.fruitdelivery.model.Supplier;


public class SupplierDto {
    // Идентификатор поставщика.
    private Long id;

    // Название поставщика.
    private String name;

    // Адрес поставщика.
    private String address;


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

    public SupplierDto(Long id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public static SupplierDto fromEntity(Supplier supplier) {
        return new SupplierDto(supplier.getId(), supplier.getName(), supplier.getAddress());
    }
}
