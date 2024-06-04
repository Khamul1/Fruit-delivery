package com.example.fruitdelivery.controller;

import com.example.fruitdelivery.dto.DeliveryDto;
import com.example.fruitdelivery.dto.DeliveryReportDto;
import com.example.fruitdelivery.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/deliveries")
public class DeliveryController {

    @Autowired
    private DeliveryService deliveryService;

    // Создание новой поставки
    @PostMapping
    public DeliveryDto createDelivery(@RequestBody DeliveryDto deliveryDto) {
        return deliveryService.createDelivery(deliveryDto);
    }

    // Получение отчета о поставках за период
    @GetMapping("/report")
    public DeliveryReportDto getReport(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
        return deliveryService.getDeliveryReport(startDate, endDate);
    }
}
