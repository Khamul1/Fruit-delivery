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
                           FruitMapper fruitMapper, FruitService fruitService, // Измените на FruitService
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

    @GetMapping
    public List<FruitDto> getAllFruits() {
        return fruitRepository.findAll().stream()
                .map(fruitMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FruitDto> getFruitById(@PathVariable Long id) {
        Fruit fruit = fruitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Фрукт с ID " + id + " не найден"));
        return ResponseEntity.ok(fruitMapper.toDto(fruit));
    }

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

    @PostMapping("/deliveries")
    public ResponseEntity<DeliveryDto> createDelivery(@RequestBody DeliveryDto deliveryDto) {
        Supplier supplier = supplierRepository.findById(deliveryDto.getSupplierId())
                .orElseThrow(() -> new ResourceNotFoundException("Поставщик не найден"));
        Delivery delivery = new Delivery();
        delivery.setSupplier(supplier);
        delivery.setDeliveryDate(deliveryDto.getDeliveryDate());// Создание DeliveryItem из DeliveryItemDto
        List<DeliveryItem> deliveryItems = deliveryDto.getItems().stream()
                .map(itemDto -> {
                    Fruit fruit = fruitRepository.findById(itemDto.getFruitId())
                            .orElseThrow(() -> new ResourceNotFoundException("Фрукт не найден"));
                    DeliveryItem item = new DeliveryItem(delivery, fruit, itemDto.getQuantity());
                    return item;
                })
                .collect(Collectors.toList());

        delivery.setItems(deliveryItems);
        Delivery createdDelivery = deliveryRepository.save(delivery);

        // Создание DeliveryDto для ответа
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

    @PostMapping("/{fruitId}/prices")
    public ResponseEntity<FruitPriceDto> createFruitPrice(@PathVariable Long fruitId, @RequestBody FruitPriceDto fruitPriceDto) {
        Fruit fruit = fruitRepository.findById(fruitId)
                .orElseThrow(() -> new ResourceNotFoundException("Фрукт не найден"));

        FruitPrice fruitPrice = new FruitPrice(
                fruitPriceDto.getSupplierId(),
                fruit,
                fruitPriceDto.getPrice(),
                fruitPriceDto.getStartDate(),
                fruitPriceDto.getEndDate()
        );


        FruitPrice createdFruitPrice = fruitPriceService.createFruitPrice(fruitPrice);
        return new ResponseEntity<>(new FruitPriceDto(
                createdFruitPrice.getId(),
                createdFruitPrice.getStartDate(),
                createdFruitPrice.getEndDate(),
                createdFruitPrice.getPrice()
        ), HttpStatus.CREATED);
    }

    @GetMapping("/{fruitId}/prices")
    public ResponseEntity<List<FruitPriceDto>> getFruitPrices(@PathVariable Long fruitId) {
        List<FruitPrice> fruitPrices = fruitPriceService.getFruitPricesByFruitId(fruitId);
        return ResponseEntity.ok(fruitPrices.stream()
                .map(fruitPriceMapper::toDto)
                .collect(Collectors.toList()));
    }

    @GetMapping("/deliveries")
    public ResponseEntity<List<DeliveryDto>> getAllDeliveries() {
        List<Delivery> deliveries = deliveryRepository.findAll();
        return ResponseEntity.ok(deliveries.stream()
                .map(deliveryMapper::toDto)
                .collect(Collectors.toList()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FruitDto> updateFruit(@PathVariable Long id, @RequestBody FruitDto fruitDto) {
        Fruit fruitToUpdate = fruitService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Фрукт с ID " + id + " не найден"));
        fruitToUpdate.setType(fruitDto.getType());
        fruitToUpdate.setVariety(fruitDto.getVariety());
        fruitToUpdate.setQuantity(fruitDto.getQuantity());
        fruitToUpdate.setWeight(fruitDto.getWeight());
        fruitToUpdate.setCost(fruitDto.getCost());
        Supplier supplier = supplierRepository.findById(fruitDto.getSupplierId())
                .orElseThrow(() -> new ResourceNotFoundException("Поставщик не найден"));

        fruitToUpdate.setSupplier(supplier);

        Fruit updatedFruit = fruitRepository.save(fruitToUpdate);
        return ResponseEntity.ok(fruitMapper.toDto(updatedFruit));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFruit(@PathVariable Long id) {
        fruitService.deleteFruit(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/supplier")
    public ResponseEntity<FruitDto> updateFruitSupplier(@PathVariable Long id, @RequestParam Long supplierId) {
        Fruit fruit = fruitService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Фрукт с ID " + id + " не найден"));
        Fruit updatedFruit = fruitService.updateFruitSupplier(id, supplierId); // Используйте updateFruitSupplier из FruitService
        return ResponseEntity.ok(fruitMapper.toDto(updatedFruit));
    }
}
