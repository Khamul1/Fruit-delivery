package com.example.fruitdelivery.test;

import com.example.fruitdelivery.dto.DeliveryDto;
import com.example.fruitdelivery.dto.DeliveryItemDto;
import com.example.fruitdelivery.dto.DeliveryReportDto;
import com.example.fruitdelivery.model.Delivery;
import com.example.fruitdelivery.model.DeliveryItem;
import com.example.fruitdelivery.model.Fruit;
import com.example.fruitdelivery.model.FruitPrice;
import com.example.fruitdelivery.repository.DeliveryRepository;
import com.example.fruitdelivery.repository.FruitPriceRepository;
import com.example.fruitdelivery.repository.FruitRepository;
import com.example.fruitdelivery.repository.SupplierRepository;
import com.example.fruitdelivery.service.DeliveryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
public class DeliveryServiceTest {

    @Autowired
    private DeliveryService deliveryService;

    @MockBean
    private DeliveryRepository deliveryRepository;

    @MockBean
    private FruitPriceRepository fruitPriceRepository;

    @MockBean
    private FruitRepository fruitRepository;

    @MockBean
    private SupplierRepository supplierRepository;

    @Test
    void createDelivery() {
        // Заглушки для данных о поставке
        DeliveryDto deliveryDto = new DeliveryDto();
        deliveryDto.setSupplierId(1L);
        deliveryDto.setDeliveryDate(LocalDate.now());
        List<DeliveryItem> items = new ArrayList<>();
        // Создаем объект Fruit
        Fruit fruit = new Fruit();
        fruit.setId(1L);
        // Создаем объект DeliveryItem
        DeliveryItem item = new DeliveryItem();
        item.setFruit(fruit);
        item.setQuantity(10);
        items.add(item);


        List<DeliveryItemDto> deliveryItemDtos = items.stream()
                .map(deliveryItem -> {
                    DeliveryItemDto deliveryItemDto = new DeliveryItemDto();
                    deliveryItemDto.setFruitId(deliveryItem.getFruit().getId());
                    deliveryItemDto.setQuantity(deliveryItem.getQuantity());
                    return deliveryItemDto;
                })
                .collect(Collectors.toList());

        // Устанавливаем преобразованный список в deliveryDto
        deliveryDto.setItems(deliveryItemDtos);

        // Заглушки для данных о фрукте и цене
        FruitPrice fruitPrice = new FruitPrice();
        fruitPrice.setFruit(fruit);
        fruitPrice.setPrice(10.0);

        // Заглушки для данных о поставщике
        when(supplierRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(new com.example.fruitdelivery.model.Supplier()));
        when(fruitRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(fruit));
        when(fruitPriceRepository.findAllByStartDateBetween(any(), any())).thenReturn(List.of(fruitPrice));
        when(deliveryRepository.save(any())).thenReturn(new Delivery());

        // Вызов метода createDelivery
        DeliveryDto createdDelivery = deliveryService.createDelivery(deliveryDto);

        // Проверка результата
        assertNotNull(createdDelivery);
        assertEquals(100.0, createdDelivery.getTotalCost());
    }

    @Test
    void getReport() {
        // Заглушки для данных о поставках
        LocalDate startDate = LocalDate.of(2024, 6, 1);
        LocalDate endDate = LocalDate.of(2024, 6, 30);
        List<Delivery> deliveries = new ArrayList<>();
        Delivery delivery = new Delivery();
        delivery.setDeliveryDate(LocalDate.of(2024, 6, 15));
        // Создаем объект Fruit
        Fruit fruit = new Fruit();
        fruit.setId(1L); // Устанавливаем идентификатор фрукта
        // Создаем объект DeliveryItem
        DeliveryItem item = new DeliveryItem();
        item.setFruit(fruit); // Устанавливаем объект Fruit в DeliveryItem
        item.setQuantity(10);
        delivery.setItems(List.of(item));
        deliveries.add(delivery);

        // Заглушки для данных о фруктах и ценах
        FruitPrice fruitPrice = new FruitPrice();
        fruitPrice.setFruit(fruit);
        fruitPrice.setPrice(10.0);
        when(deliveryRepository.findAllByDeliveryDateBetween(startDate, endDate)).thenReturn(deliveries);
        when(fruitRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(fruit));

        // Вызов метода getReport
        DeliveryReportDto report = deliveryService.getDeliveryReport(startDate, endDate);// Проверка результата
        assertNotNull(report);
        assertEquals(1, report.getItems().size());
        assertEquals(100.0, report.getItems().get(0).getTotalCost());
        assertEquals(10, report.getItems().get(0).getQuantity());
        assertEquals(10.0, report.getItems().get(0).getTotalWeight());
    }
}
