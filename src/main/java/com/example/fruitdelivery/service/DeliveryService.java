package com.example.fruitdelivery.service;

import com.example.fruitdelivery.dto.DeliveryDto;
import com.example.fruitdelivery.dto.DeliveryReportDto;
import com.example.fruitdelivery.dto.DeliveryReportItemDto;
import com.example.fruitdelivery.model.Delivery;
import com.example.fruitdelivery.model.DeliveryItem;
import com.example.fruitdelivery.model.Fruit;
import com.example.fruitdelivery.model.FruitPrice;
import com.example.fruitdelivery.repository.DeliveryRepository;
import com.example.fruitdelivery.repository.FruitPriceRepository;
import com.example.fruitdelivery.repository.DeliveryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DeliveryService {

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private FruitPriceRepository fruitPriceRepository;

    @Autowired
    private DeliveryMapper deliveryMapper;

    public DeliveryDto createDelivery(DeliveryDto deliveryDto) {        Delivery delivery = deliveryMapper.toEntity(deliveryDto);

        // Рассчитываем общую стоимость доставки
        double totalCost = 0;
        for (DeliveryItem item : delivery.getItems()) {
            Fruit fruit = item.getFruit();
            Optional<FruitPrice> fruitPrice = fruitPriceRepository.findByFruitIdAndStartDate(fruit.getId(), delivery.getDeliveryDate());
            if (fruitPrice.isPresent()) {
                totalCost += fruitPrice.get().getPrice() * item.getQuantity();
            } else {
                // Обработка случая, когда цена на фрукт не найдена
                throw new IllegalArgumentException("Цена на фрукт не найдена");
            }
        }
        delivery.setTotalCost(totalCost);

        try {
            deliveryRepository.save(delivery);
        } catch (Exception e) {
            // Обработка ошибки при сохранении доставки
            throw new RuntimeException("Ошибка при сохранении доставки", e);
        }

        return deliveryMapper.toDto(delivery);
    }

    public List<DeliveryDto> getDeliveriesByDates(LocalDate startDate, LocalDate endDate) {
        List<Delivery> deliveries;
        try {
            deliveries = deliveryRepository.findAllByDeliveryDateBetween(startDate, endDate);
        } catch (Exception e) {
            // Обработка ошибки при получении данных из базы данных
            throw new RuntimeException("Ошибка при получении данных о доставках", e);
        }

        return deliveries.stream()
                .map(deliveryMapper::toDto)
                .collect(Collectors.toList());
    }

    public DeliveryReportDto getDeliveryReport(LocalDate startDate, LocalDate endDate) {
        List<FruitPrice> fruitPrices;
        List<Delivery> deliveries;
        try {
            fruitPrices = fruitPriceRepository.findAllByStartDateBetween(startDate, endDate);
            deliveries = deliveryRepository.findAllByDeliveryDateBetween(startDate, endDate);
        } catch (Exception e) {
            // Обработка ошибки при получении данных из базы данных
            throw new RuntimeException("Ошибка при получении данных для отчета", e);
        }

        // Создаем Map для хранения цен на фрукты
        Map<Long, FruitPrice> fruitPriceMap = fruitPrices.stream()
                .collect(Collectors.toMap(fp -> fp.getFruit().getId(), fp -> fp));List<DeliveryReportItemDto> deliveryReportItems = new ArrayList<>();
        for (Delivery delivery : deliveries) {
            for (DeliveryItem item : delivery.getItems()) {
                Fruit fruit = item.getFruit();
                FruitPrice fruitPrice = fruitPriceMap.get(fruit.getId());
                if (fruitPrice != null) {
                    DeliveryReportItemDto deliveryReportItemDto = new DeliveryReportItemDto();
                    deliveryReportItemDto.setFruit(fruit);
                    deliveryReportItemDto.setQuantity(item.getQuantity());
                    deliveryReportItemDto.setPricePerUnit(fruitPrice.getPrice());
                    deliveryReportItemDto.setWeightPerUnit(fruit.getWeight());
                    deliveryReportItems.add(deliveryReportItemDto);
                }
            }
        }

        DeliveryReportDto deliveryReportDto = new DeliveryReportDto();
        deliveryReportDto.setItems(deliveryReportItems);
        return deliveryReportDto;
    }
}

