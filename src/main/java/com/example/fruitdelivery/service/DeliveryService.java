package com.example.fruitdelivery.service;

import com.example.fruitdelivery.dto.DeliveryDto;
import com.example.fruitdelivery.dto.DeliveryItemDto;
import com.example.fruitdelivery.dto.DeliveryReportDto;
import com.example.fruitdelivery.dto.DeliveryReportItemDto;
import com.example.fruitdelivery.model.Delivery;
import com.example.fruitdelivery.model.DeliveryItem;
import com.example.fruitdelivery.model.Fruit;
import com.example.fruitdelivery.model.FruitPrice;
import com.example.fruitdelivery.model.Supplier;
import com.example.fruitdelivery.repository.*;
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
    private FruitRepository fruitRepository;

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private FruitPriceRepository fruitPriceRepository;

    @Autowired
    private DeliveryMapper deliveryMapper;

    @Autowired
    private SupplierRepository supplierRepository;

    public DeliveryDto createDelivery(DeliveryDto deliveryDto) {
        // Получаем существующий Supplier из базы данных
        Supplier supplier = supplierRepository.findById(deliveryDto.getSupplierId())
                .orElseThrow(() -> new IllegalArgumentException("Поставщик с ID " + deliveryDto.getSupplierId() + " не найден"));

        // Создаем объект Delivery
        Delivery delivery = new Delivery();
        delivery.setSupplier(supplier);
        delivery.setDeliveryDate(deliveryDto.getDeliveryDate());
        delivery.setItems(mapItemsToEntity(deliveryDto.getItems()));

        // Получаем цены на фрукты за период
        Map<Long, FruitPrice> fruitPriceMap = fruitPriceRepository.findAllByStartDateBetween(deliveryDto.getDeliveryDate(), deliveryDto.getDeliveryDate())
                .stream()
                .collect(Collectors.toMap(fp -> fp.getFruit().getId(), fp -> fp));

        // Рассчитываем общую стоимость доставки
        double totalCost = 0;
        for (DeliveryItem item : delivery.getItems()) {
            Fruit fruit = item.getFruit();
            FruitPrice fruitPrice = fruitPriceMap.get(fruit.getId());
            if (fruitPrice != null) {
                totalCost += fruitPrice.getPrice() * item.getQuantity();
            } else {
                // Обработка случая, когда цена на фрукт не найдена
                throw new IllegalArgumentException("Цена на фрукт не найдена");
            }
        }
        delivery.setTotalCost(totalCost);

        try {
            delivery = deliveryRepository.save(delivery); // Сохраняем объект Delivery
        } catch (Exception e) {
            // Обработка ошибки при сохранении доставки
            throw new RuntimeException("Ошибка при сохранении доставки", e);
        }

        // Преобразуем Delivery обратно в DeliveryDto
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
        List<Delivery> deliveries;
        try {
            deliveries = deliveryRepository.findAllByDeliveryDateBetween(startDate, endDate);
        } catch (Exception e) {
            // Обработка ошибки при получении данных из базы данных
            throw new RuntimeException("Ошибка при получении данных для отчета", e);
        }

        List<DeliveryReportItemDto> deliveryReportItems = new ArrayList<>();
        for (Delivery delivery : deliveries) {
            for (DeliveryItem item : delivery.getItems()) {
                Fruit fruit = item.getFruit();
                Optional<FruitPrice> fruitPrice = fruitPriceRepository.findByFruitIdAndStartDate(fruit.getId(), delivery.getDeliveryDate());
                if (fruitPrice.isPresent()) {
                    DeliveryReportItemDto deliveryReportItemDto = new DeliveryReportItemDto();
                    deliveryReportItemDto.setFruit(fruit);
                    deliveryReportItemDto.setQuantity(item.getQuantity());
                    deliveryReportItemDto.setPricePerUnit(fruitPrice.get().getPrice());
                    deliveryReportItemDto.setWeightPerUnit(fruit.getWeight());
                    deliveryReportItems.add(deliveryReportItemDto);
                }
            }
        }

        // Создаем DeliveryReportDto с помощью deliveryReportItems
        DeliveryReportDto deliveryReportDto = new DeliveryReportDto();
        deliveryReportDto.setItems(deliveryReportItems);
        return deliveryReportDto;
    }


    private List<DeliveryItem> mapItemsToEntity(List<DeliveryItemDto> deliveryItemDtos) {
        List<DeliveryItem> deliveryItems = new ArrayList<>();
        for (DeliveryItemDto deliveryItemDto : deliveryItemDtos) {
            DeliveryItem deliveryItem = new DeliveryItem();
            deliveryItem.setId(deliveryItemDto.getId());
            // Получаем Fruit из репозитория по ID
            Fruit fruit = fruitRepository.findById(deliveryItemDto.getFruitId())
                    .orElseThrow(() -> new IllegalArgumentException("Фрукт с ID " + deliveryItemDto.getFruitId() + " не найден"));
            deliveryItem.setFruit(fruit);
            deliveryItem.setQuantity(deliveryItemDto.getQuantity());
            deliveryItems.add(deliveryItem);
        }
        return deliveryItems;
    }
}