package com.example.fruitdelivery.service;

import com.example.fruitdelivery.model.Fruit;

import java.util.Optional;

public interface FruitService {
    Optional<Fruit> findById(Long fruitId);
    Fruit updateFruitSupplier(Long fruitId, Long supplierId);
    void deleteFruit(Long id);
}
