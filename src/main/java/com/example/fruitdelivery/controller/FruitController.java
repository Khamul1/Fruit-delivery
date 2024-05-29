package com.example.fruitdelivery.controller;

import com.example.fruitdelivery.dto.FruitDto;
import com.example.fruitdelivery.model.Fruit;
import com.example.fruitdelivery.model.Supplier;
import com.example.fruitdelivery.repository.FruitRepository;
import com.example.fruitdelivery.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.fruitdelivery.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/fruits")
public class FruitController {

    private final FruitRepository fruitRepository;
    private final SupplierRepository supplierRepository;

    @Autowired
    public FruitController(FruitRepository fruitRepository, SupplierRepository supplierRepository) {
        this.fruitRepository = fruitRepository;
        this.supplierRepository = supplierRepository;
    }

    @GetMapping
    public List<Fruit> getAllFruits() {
        return fruitRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Fruit> getFruitById(@PathVariable Long id) {
        Optional<Fruit> fruit = fruitRepository.findById(id);
        if (fruit.isPresent()) {
            return ResponseEntity.ok(fruit.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<FruitDto> createFruit(@RequestBody FruitDto fruitDto) {
        Supplier supplier = supplierRepository.findById(fruitDto.getSupplierId())
                .orElseThrow(() -> new ResourceNotFoundException("Поставщик не найден"));

        Fruit fruit = new Fruit(
                null, // ID будет сгенерирован автоматически
                fruitDto.getType(),
                fruitDto.getVariety(),
                fruitDto.getQuantity(),
                fruitDto.getWeight(),
                fruitDto.getCost(),
                supplier
        );

        Fruit createdFruit = fruitRepository.save(fruit);
        FruitDto responseDto = new FruitDto(
                createdFruit.getId(),
                createdFruit.getType(),
                createdFruit.getVariety(),
                createdFruit.getQuantity(),
                createdFruit.getWeight(),
                createdFruit.getCost(),
                createdFruit.getSupplier().getId()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Fruit> updateFruit(@PathVariable Long id, @RequestBody FruitDto fruitDto) {
        Optional<Fruit> existingFruit = fruitRepository.findById(id);
        if (existingFruit.isPresent()) {
            Fruit fruitToUpdate = existingFruit.get();
            fruitToUpdate.setType(fruitDto.getType());
            fruitToUpdate.setVariety(fruitDto.getVariety());
            fruitToUpdate.setQuantity(fruitDto.getQuantity());
            fruitToUpdate.setWeight(fruitDto.getWeight());
            fruitToUpdate.setCost(fruitDto.getCost());

            Supplier supplier = supplierRepository.findById(fruitDto.getSupplierId())
                    .orElseThrow(() -> new ResourceNotFoundException("Поставщик не найден"));

            fruitToUpdate.setSupplier(supplier);

            Fruit updatedFruit = fruitRepository.save(fruitToUpdate);
            return ResponseEntity.ok(updatedFruit);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFruit(@PathVariable Long id) {
        fruitRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
