package com.example.fruitdelivery.service;

import com.example.fruitdelivery.exception.ResourceNotFoundException;
import com.example.fruitdelivery.exception.SupplierNotFoundException;
import com.example.fruitdelivery.model.Fruit;
import com.example.fruitdelivery.model.Supplier;
import com.example.fruitdelivery.repository.FruitRepository;
import com.example.fruitdelivery.repository.SupplierRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FruitServiceImpl implements FruitService {

    private static final Logger logger = LoggerFactory.getLogger(FruitServiceImpl.class);

    @Autowired
    private FruitRepository fruitRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Override
    public Optional<Fruit> findById(Long id) {
        return fruitRepository.findById(id);
    }

    public Fruit updateFruit(Long id, Fruit fruit) {
        Optional<Fruit> existingFruit = fruitRepository.findById(id);
        if (existingFruit.isPresent()) {
            existingFruit.get().setType(fruit.getType());
            existingFruit.get().setVariety(fruit.getVariety());
            existingFruit.get().setQuantity(fruit.getQuantity());
            existingFruit.get().setWeight(fruit.getWeight());
            existingFruit.get().setCost(fruit.getCost());
            return fruitRepository.save(existingFruit.get());
        } else {
            throw new ResourceNotFoundException("Фрукт с ID " + id + " не найден");
        }
    }

    public void deleteFruit(Long id) {
        logger.info("Удаление фрукта с ID: {}", id);
        fruitRepository.findById(id).ifPresentOrElse(fruitRepository::delete, () -> {
            logger.error("Фрукт с ID {} не найден", id);
            throw new ResourceNotFoundException("Фрукт с ID " + id + " не найден");
        });
    }


    public Fruit updateFruitSupplier(Long fruitId, Long supplierId) {
        logger.info("Обновление поставщика для фрукта с ID: {}", fruitId);
        Optional<Fruit> fruit = fruitRepository.findById(fruitId);
        if (fruit.isPresent()) {
            Supplier supplier = supplierRepository.findById(supplierId)
                    .orElseThrow(() -> new SupplierNotFoundException("Поставщик с ID " + supplierId + " не найден"));
            fruit.get().setSupplier(supplier);
            return fruitRepository.save(fruit.get());
        } else {
            throw new ResourceNotFoundException("Фрукт с ID " + fruitId + " не найден");
        }
    }
}
