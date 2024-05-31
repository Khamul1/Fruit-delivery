package com.example.fruitdelivery.service;

import com.example.fruitdelivery.exception.ResourceNotFoundException;
import com.example.fruitdelivery.model.FruitPrice;
import com.example.fruitdelivery.repository.FruitPriceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FruitPriceService {

    private static final Logger logger = LoggerFactory.getLogger(FruitPriceService.class);

    private final FruitPriceRepository fruitPriceRepository;

    @Autowired
    public FruitPriceService(FruitPriceRepository fruitPriceRepository) {
        this.fruitPriceRepository = fruitPriceRepository;
    }

    public FruitPrice createFruitPrice(FruitPrice fruitPrice) {
        return fruitPriceRepository.save(fruitPrice);
    }

    public FruitPrice getFruitPriceById(Long id) {
        Optional<FruitPrice> fruitPrice = fruitPriceRepository.findById(id);
        if (fruitPrice.isPresent()) {
            return fruitPrice.get();
        } else {
            logger.error("Цена фрукта не найдена с ID: {}", id);
            throw new ResourceNotFoundException("Цена фрукта не найдена с ID: " + id);
        }
    }

    public List<FruitPrice> getFruitPricesByFruitId(Long fruitId) {
        return fruitPriceRepository.findAllByFruitId(fruitId);
    }
}
