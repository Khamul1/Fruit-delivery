package com.example.fruitdelivery.repository;

import com.example.fruitdelivery.model.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    List<Delivery> findAllByDeliveryDateBetween(LocalDate startDate, LocalDate endDate);
}
