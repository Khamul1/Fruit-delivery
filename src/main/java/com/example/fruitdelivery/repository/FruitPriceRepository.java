package com.example.fruitdelivery.repository;

import com.example.fruitdelivery.model.FruitPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FruitPriceRepository extends JpaRepository<FruitPrice, Long> {

    List<FruitPrice> findAllByFruitId(Long fruitId);
}
