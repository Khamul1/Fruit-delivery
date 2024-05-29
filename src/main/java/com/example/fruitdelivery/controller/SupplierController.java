package com.example.fruitdelivery.controller;

import com.example.fruitdelivery.dto.SupplierDto;
import com.example.fruitdelivery.model.Supplier;
import com.example.fruitdelivery.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/suppliers") // Измените путь
public class SupplierController {

    private final SupplierRepository supplierRepository;

    @Autowired
    public SupplierController(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @GetMapping
    public List<Supplier> getAllSuppliers() {
        return supplierRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Supplier> getSupplierById(@PathVariable Long id) {
        Optional<Supplier> supplier = supplierRepository.findById(id);
        if (supplier.isPresent()) {
            if (supplier.get().getAddress() == null) {
                supplier.get().setAddress("Не указан");
            }
            return ResponseEntity.ok(supplier.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping
    public ResponseEntity<SupplierDto> createSupplier(@RequestBody SupplierDto supplierDto) {
        // Создайте новый объект Supplier, используя данные из supplierDto
        Supplier supplier = new Supplier(
                supplierDto.getName(),
                supplierDto.getAddress()
        );

        Supplier createdSupplier = supplierRepository.save(supplier);
        SupplierDto responseDto = new SupplierDto(
                createdSupplier.getId(),
                createdSupplier.getName(),
                supplierDto.getAddress()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }



    @PutMapping("/{id}")
    public ResponseEntity<Supplier> updateSupplier(@PathVariable Long id, @RequestBody SupplierDto supplierDto) {
        Optional<Supplier> existingSupplier = supplierRepository.findById(id);
        if (existingSupplier.isPresent()) {
            Supplier supplierToUpdate = existingSupplier.get();
            supplierToUpdate.setName(supplierDto.getName());
            supplierToUpdate.setAddress(supplierDto.getAddress());

            Supplier updatedSupplier = supplierRepository.save(supplierToUpdate);
            return ResponseEntity.ok(updatedSupplier);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSupplier(@PathVariable Long id) {
        supplierRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
