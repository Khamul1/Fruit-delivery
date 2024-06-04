package com.example.fruitdelivery.controller;

import com.example.fruitdelivery.dto.SupplierDto;
import com.example.fruitdelivery.exception.ResourceNotFoundException;
import com.example.fruitdelivery.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/suppliers")
@Validated
public class SupplierController {

    private final SupplierService supplierService;

    @Autowired
    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    // Получение списка всех поставщиков
    @GetMapping
    public List<SupplierDto> getAllSuppliers() {
        return supplierService.getAllSuppliers();
    }

    // Получение поставщика по ID
    @GetMapping("/{id}")
    public ResponseEntity<SupplierDto> getSupplierById(@PathVariable Long id) {
        Optional<SupplierDto> supplierDto = supplierService.getSupplierById(id);
        return supplierDto.map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Поставщик с ID " + id + " не найден"));
    }

    // Создание нового поставщика
    @PostMapping
    public ResponseEntity<SupplierDto> createSupplier(@Valid @RequestBody SupplierDto supplierDto) {
        SupplierDto createdSupplier = supplierService.createSupplier(supplierDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSupplier);
    }

    // Обновление поставщика по ID
    @PutMapping("/{id}")
    public ResponseEntity<SupplierDto> updateSupplier(@PathVariable Long id, @Valid @RequestBody SupplierDto supplierDto) {
        Optional<SupplierDto> updatedSupplier = supplierService.updateSupplier(id, supplierDto);
        return updatedSupplier.map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Поставщик с ID " + id + " не найден"));
    }

    // Удаление поставщика по ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSupplier(@PathVariable Long id) {
        supplierService.deleteSupplier(id);
        return ResponseEntity.noContent().build();
    }

    // Удаление дубликатов поставщиков
    @PostMapping("/delete_duplicates")
    public ResponseEntity<String> deleteDuplicateSuppliers() {
        // Получаем список всех поставщиков
        List<SupplierDto> suppliers = supplierService.getAllSuppliers();

        // Создаем список уникальных адресов
        Set<String> uniqueAddresses = new HashSet<>();

        // Фильтруем список поставщиков, оставляя только тех, у которых уникальный адрес
        List<SupplierDto> uniqueSuppliers = suppliers.stream()
                .filter(supplier -> uniqueAddresses.add(supplier.getAddress()))
                .collect(Collectors.toList());

        // Удаляем дубликаты из базы данных
        suppliers.stream()
                .filter(supplier -> !uniqueSuppliers.contains(supplier))
                .forEach(supplier -> supplierService.deleteSupplier(supplier.getId()));

        // Возвращаем сообщение об успешном удалении дубликатов
        return ResponseEntity.ok("Дубликаты поставщиков удалены");
    }
}
