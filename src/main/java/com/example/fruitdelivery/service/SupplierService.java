package com.example.fruitdelivery.service;

import com.example.fruitdelivery.dto.DeliveryDto;
import com.example.fruitdelivery.dto.DeliveryReportDto;
import com.example.fruitdelivery.dto.DeliveryReportItemDto;
import com.example.fruitdelivery.dto.SupplierDto;
import com.example.fruitdelivery.exception.ResourceNotFoundException;
import com.example.fruitdelivery.model.Delivery;
import com.example.fruitdelivery.model.DeliveryItem;
import com.example.fruitdelivery.model.Fruit;
import com.example.fruitdelivery.model.FruitPrice;
import com.example.fruitdelivery.model.Supplier;
import com.example.fruitdelivery.repository.DeliveryRepository;
import com.example.fruitdelivery.repository.FruitPriceRepository;
import com.example.fruitdelivery.repository.DeliveryMapper;
import com.example.fruitdelivery.repository.SupplierRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SupplierService {

    private static final Logger logger = LoggerFactory.getLogger(SupplierService.class);

    @Autowired
    private SupplierRepository supplierRepository;

    public List<SupplierDto> getAllSuppliers() {
        logger.info("Получение всех поставщиков");
        return supplierRepository.findAll().stream()
                .map(SupplierDto::fromEntity)
                .collect(Collectors.toList());
    }

    public Optional<SupplierDto> getSupplierById(Long id) {
        logger.info("Получение поставщика с id: {}", id);
        Optional<Supplier> supplier = supplierRepository.findById(id);
        return supplier.map(SupplierDto::fromEntity);
    }

    public SupplierDto createSupplier(SupplierDto supplierDto) {
        logger.info("Создание нового поставщика: {} с именем {} и адресом {}", supplierDto, supplierDto.getName(), supplierDto.getAddress());

        // Проверка на дубликаты
        Optional<Supplier> existingSupplier = supplierRepository.findByAddress(supplierDto.getAddress());
        if (existingSupplier.isPresent()) {

            throw new IllegalArgumentException("Поставщик с таким адресом уже существует");
        }

        Supplier supplier = new Supplier(supplierDto.getName(), supplierDto.getAddress());
        Supplier createdSupplier = supplierRepository.save(supplier);
        return SupplierDto.fromEntity(createdSupplier);
    }

    public Optional<SupplierDto> updateSupplier(Long id, SupplierDto supplierDto) {
        logger.info("Обновление поставщика с id: {}: {}", id, supplierDto);
        Optional<Supplier> existingSupplier = supplierRepository.findById(id);
        return existingSupplier.map(supplier -> {
            supplier.setName(supplierDto.getName());
            supplier.setAddress(supplierDto.getAddress());
            return SupplierDto.fromEntity(supplierRepository.save(supplier));
        });
    }

    public void deleteSupplier(Long id) {
        logger.info("Удаление поставщика с id: {}", id);
        supplierRepository.deleteById(id);
    }
}