package com.example.fruitdelivery.controller;

import com.example.fruitdelivery.dto.DeliveryDto;
import com.example.fruitdelivery.dto.DeliveryItemDto;
import com.example.fruitdelivery.dto.FruitDto;
import com.example.fruitdelivery.dto.FruitPriceDto;
import com.example.fruitdelivery.exception.ResourceNotFoundException;
import com.example.fruitdelivery.model.*;
import com.example.fruitdelivery.repository.*;
import com.example.fruitdelivery.service.FruitPriceService;
import com.example.fruitdelivery.service.FruitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/fruits")
public class FruitController {

    private final FruitRepository fruitRepository;
    private final SupplierRepository supplierRepository;
    private final FruitPriceService fruitPriceService;
    private final DeliveryRepository deliveryRepository;
    private final FruitMapper fruitMapper;
    private final FruitService fruitService;
    private final FruitPriceMapper fruitPriceMapper;
    private final DeliveryMapper deliveryMapper;

    @Autowired
    public FruitController(FruitRepository fruitRepository, SupplierRepository supplierRepository,
                           FruitPriceService fruitPriceService, DeliveryRepository deliveryRepository,
                           FruitMapper fruitMapper, FruitService fruitService,
                           FruitPriceMapper fruitPriceMapper, DeliveryMapper deliveryMapper) {
        this.fruitRepository = fruitRepository;
        this.supplierRepository = supplierRepository;
        this.fruitPriceService = fruitPriceService;
        this.deliveryRepository = deliveryRepository;
        this.fruitMapper = fruitMapper;
        this.fruitService = fruitService;
        this.fruitPriceMapper = fruitPriceMapper;
        this.deliveryMapper = deliveryMapper;
    }

    // Получение списка всех фруктов
    @GetMapping
    public List<FruitDto> getAllFruits() {
        return fruitRepository.findAll().stream()
                .map(fruitMapper::toDto)
                .collect(Collectors.toList());
    }

    // Получение фрукта по ID
    @GetMapping("/{id}")
    public ResponseEntity<FruitDto> getFruitById(@PathVariable Long id) {
        Fruit fruit = fruitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Фрукт с ID " + id + " не найден"));
        return ResponseEntity.ok(fruitMapper.toDto(fruit));
    }

    // Создание нового фрукта
    @PostMapping
    public ResponseEntity<FruitDto> createFruit(@RequestBody FruitDto fruitDto) {
        Supplier supplier = supplierRepository.findById(fruitDto.getSupplierId())
                .orElseThrow(() -> new ResourceNotFoundException("Поставщик не найден"));

        Fruit fruit = new Fruit(
                fruitDto.getType(),
                fruitDto.getVariety(),
                fruitDto.getQuantity(),
                fruitDto.getWeight(),
                fruitDto.getCost(),
                supplier
        );

        Fruit createdFruit = fruitRepository.save(fruit);
        return ResponseEntity.status(HttpStatus.CREATED).body(fruitMapper.toDto(createdFruit));
    }

    // Создание новой поставки
    @PostMapping("/deliveries")
    public ResponseEntity<DeliveryDto> createDelivery(@RequestBody DeliveryDto deliveryDto) {
        // Находим поставщика по ID
        Supplier supplier = supplierRepository.findById(deliveryDto.getSupplierId())
                .orElseThrow(() -> new ResourceNotFoundException("Поставщик не найден"));

        // Создаем новую поставку
        Delivery delivery = new Delivery();
        delivery.setSupplier(supplier);
        delivery.setDeliveryDate(deliveryDto.getDeliveryDate());

        // Создаем список DeliveryItem из DeliveryItemDto
        List<DeliveryItem> deliveryItems = deliveryDto.getItems().stream()
                .map(itemDto -> {
                    // Находим фрукт по ID
                    Fruit fruit = fruitRepository.findById(itemDto.getFruitId())
                            .orElseThrow(() -> new ResourceNotFoundException("Фрукт не найден"));

                    // Создаем DeliveryItem
                    DeliveryItem item = new DeliveryItem(delivery, fruit, itemDto.getQuantity());
                    return item;
                })
                .collect(Collectors.toList());

        // Добавляем список DeliveryItem в поставку
        delivery.setItems(deliveryItems);

        // Сохраняем поставку в базе данных
        Delivery createdDelivery = deliveryRepository.save(delivery);

        // Создаем DeliveryDto для ответа
        DeliveryDto responseDto = new DeliveryDto();
        responseDto.setSupplierId(createdDelivery.getSupplier().getId());
        responseDto.setDeliveryDate(createdDelivery.getDeliveryDate());
        responseDto.setItems(createdDelivery.getItems().stream()
                .map(item -> {
                    DeliveryItemDto itemDto = new DeliveryItemDto();
                    itemDto.setFruitId(item.getFruit().getId());
                    itemDto.setQuantity(item.getQuantity());
                    return itemDto;
                })
                .collect(Collectors.toList()));

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    // Создание новой цены на фрукт
    @PostMapping("/{fruitId}/prices")
    public ResponseEntity<FruitPriceDto> createFruitPrice(@PathVariable Long fruitId, @RequestBody FruitPriceDto fruitPriceDto) {
        // Находим фрукт по ID
        Fruit fruit = fruitRepository.findById(fruitId)
                .orElseThrow(() -> new ResourceNotFoundException("Фрукт не найден"));

        // Создаем новую цену на фрукт
        FruitPrice fruitPrice = new FruitPrice(
                fruitPriceDto.getSupplierId(),
                fruit,
                fruitPriceDto.getPrice(),
                fruitPriceDto.getStartDate(),
                fruitPriceDto.getEndDate()
        );

        // Сохраняем цену на фрукт в базе данных
        FruitPrice createdFruitPrice = fruitPriceService.createFruitPrice(fruitPrice);

        // Создаем FruitPriceDto для ответа
        return new ResponseEntity<>(new FruitPriceDto(
                createdFruitPrice.getId(),
                createdFruitPrice.getStartDate(),
                createdFruitPrice.getEndDate(),
                createdFruitPrice.getPrice()
        ), HttpStatus.CREATED);
    }

    // Получение цен на фрукт по ID
    @GetMapping("/{fruitId}/prices")
    public ResponseEntity<List<FruitPriceDto>> getFruitPrices(@PathVariable Long fruitId) {
        // Получаем список цен на фрукт по ID
        List<FruitPrice> fruitPrices = fruitPriceService.getFruitPricesByFruitId(fruitId);

        // Преобразуем список FruitPrice в список FruitPriceDto
        return ResponseEntity.ok(fruitPrices.stream()
                .map(fruitPriceMapper::toDto)
                .collect(Collectors.toList()));
    }

    // Получение списка всех поставок
    @GetMapping("/deliveries")
    public ResponseEntity<List<DeliveryDto>> getAllDeliveries() {
        // Получаем список всех поставок
        List<Delivery> deliveries = deliveryRepository.findAll();

        // Преобразуем список Delivery в список DeliveryDto
        return ResponseEntity.ok(deliveries.stream()
                .map(deliveryMapper::toDto)
                .collect(Collectors.toList()));
    }

    // Обновление фрукта по ID
    @PutMapping("/{id}")
    public ResponseEntity<FruitDto> updateFruit(@PathVariable Long id, @RequestBody FruitDto fruitDto) {
        // Находим фрукт по ID
        Fruit fruitToUpdate = fruitService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Фрукт с ID " + id + " не найден"));

        // Обновляем данные фрукта
        fruitToUpdate.setType(fruitDto.getType());
        fruitToUpdate.setVariety(fruitDto.getVariety());
        fruitToUpdate.setQuantity(fruitDto.getQuantity());
        fruitToUpdate.setWeight(fruitDto.getWeight());
        fruitToUpdate.setCost(fruitDto.getCost());

        // Находим поставщика по ID
        Supplier supplier = supplierRepository.findById(fruitDto.getSupplierId())
                .orElseThrow(() -> new ResourceNotFoundException("Поставщик не найден"));

        // Обновляем поставщика для фрукта
        fruitToUpdate.setSupplier(supplier);

        // Сохраняем обновленный фрукт в базе данных
        Fruit updatedFruit = fruitRepository.save(fruitToUpdate);

        // Возвращаем обновленный FruitDto
        return ResponseEntity.ok(fruitMapper.toDto(updatedFruit));
    }

    // Удаление фрукта по ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFruit(@PathVariable Long id) {
        // Удаляем фрукт по ID
        fruitService.deleteFruit(id);

        // Возвращаем пустой ответ с кодом 204 (No Content)
        return ResponseEntity.noContent().build();
    }

    // Обновление поставщика для фрукта по ID
    @PutMapping("/{id}/supplier")
    public ResponseEntity<FruitDto> updateFruitSupplier(@PathVariable Long id, @RequestParam Long supplierId) {
        // Находим фрукт по ID
        Fruit fruit = fruitService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Фрукт с ID " + id + " не найден"));

        // Обновляем поставщика для фрукта
        Fruit updatedFruit = fruitService.updateFruitSupplier(id, supplierId);

        // Возвращаем обновленный FruitDto
        return ResponseEntity.ok(fruitMapper.toDto(updatedFruit));
    }
}
