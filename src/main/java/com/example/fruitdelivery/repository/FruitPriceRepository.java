package com.example.fruitdelivery.repository;

import com.example.fruitdelivery.model.FruitPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface FruitPriceRepository extends JpaRepository<FruitPrice, Long> {

    List<FruitPrice> findAllByFruitId(Long fruitId);
    Optional<FruitPrice> findByFruitIdAndStartDate(Long fruitId, LocalDate startDate);
    List<FruitPrice> findAllByStartDateBetween(LocalDate startDate, LocalDate endDate);
}
