package com.example.fruitdelivery.test;

import com.example.fruitdelivery.dto.DeliveryDto;
import com.example.fruitdelivery.dto.DeliveryItemDto;
import com.example.fruitdelivery.dto.DeliveryReportDto;
import com.example.fruitdelivery.dto.DeliveryReportItemDto;
import com.example.fruitdelivery.model.*;
import com.example.fruitdelivery.repository.DeliveryRepository;
import com.example.fruitdelivery.repository.FruitPriceRepository;
import com.example.fruitdelivery.repository.FruitRepository;
import com.example.fruitdelivery.repository.SupplierRepository;
import com.example.fruitdelivery.service.DeliveryService;
import com.example.fruitdelivery.repository.DeliveryMapperImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.stereotype.Component; // Импортируйте правильную аннотацию

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@SpringBootTest
@ActiveProfiles("test")
@Component
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

    // Объявляем переменную supplier
    private Supplier supplier;

    @Test
    void createDelivery() {
        // Заглушки для данных о поставке
        Supplier supplier = new Supplier();
        supplier.setId(1L); // Установите ID поставщика
        when(supplierRepository.findById(1L)).thenReturn(Optional.of(supplier));

        DeliveryDto deliveryDto = new DeliveryDto();
        deliveryDto.setSupplierId(1L);
        deliveryDto.setDeliveryDate(LocalDate.now());

        // Создаем объект Fruit
        Fruit fruit = new Fruit();
        fruit.setId(34L);
        fruit.setWeight(1.0); // Установите вес фрукта

        // Создаем объект DeliveryItemDto
        DeliveryItemDto deliveryItemDto = new DeliveryItemDto();
        deliveryItemDto.setFruitId(fruit.getId());
        deliveryItemDto.setQuantity(10);

        // Добавляем DeliveryItemDto в список items в deliveryDto
        List<DeliveryItemDto> items = new ArrayList<>();
        items.add(deliveryItemDto);
        deliveryDto.setItems(items);

        // Заглушки для данных о фрукте и цене
        FruitPrice fruitPrice = new FruitPrice();
        fruitPrice.setFruit(fruit);
        fruitPrice.setPrice(10.0); // Установите цену для фрукта
        List<FruitPrice> fruitPrices = new ArrayList<>();
        fruitPrices.add(fruitPrice);
        when(fruitPriceRepository.findAllByStartDateBetween(deliveryDto.getDeliveryDate(), deliveryDto.getDeliveryDate())).thenReturn(fruitPrices);

        // Заглушка для метода save в репозитории
        Delivery delivery = new Delivery();
        delivery.setSupplier(supplier);
        delivery.setDeliveryDate(deliveryDto.getDeliveryDate());
        delivery.setItems(new ArrayList<>()); // Инициализируем список items в delivery
        when(deliveryRepository.save(any())).thenReturn(delivery);

        // Вызов метода createDelivery
        DeliveryDto createdDelivery = deliveryService.createDelivery(deliveryDto);
        // Проверка результата
        assertNotNull(createdDelivery);
        assertEquals(100.0, createdDelivery.getTotalCost()); // Ожидаем 100.0
    }

    @Test
    void getReport() {
        // Заглушки для данных о поставках
        LocalDate startDate = LocalDate.of(2024, 6, 1);
        LocalDate endDate = LocalDate.of(2024, 6, 30);
        List<Delivery> deliveries = new ArrayList<>();
        Delivery delivery1 = new Delivery();
        delivery1.setDeliveryDate(LocalDate.of(2024, 6, 15));
        // Создаем объект Fruit
        Fruit fruit = new Fruit();
        fruit.setId(1L); // Устанавливаем идентификатор фрукта
        fruit.setWeight(1.0); // Установите вес фрукта
        // Создаем объект DeliveryItem
        DeliveryItem item1 = new DeliveryItem();
        item1.setFruit(fruit); // Устанавливаем объект Fruit в DeliveryItem
        item1.setQuantity(10);
        item1.setDelivery(delivery1); // Установите объект Delivery в DeliveryItem
        delivery1.setItems(List.of(item1)); // Добавляем DeliveryItem в список items объекта Delivery
        deliveries.add(delivery1); // Добавляем поставку в список

        // Заглушки для данных о фруктах и ценах
        FruitPrice fruitPrice = new FruitPrice();
        fruitPrice.setFruit(fruit);
        fruitPrice.setPrice(10.0); // Установите цену для фрукта

        when(deliveryRepository.findAllByDeliveryDateBetween(startDate, endDate)).thenReturn(deliveries);
        when(fruitRepository.findById(1L)).thenReturn(Optional.ofNullable(fruit));
        when(fruitPriceRepository.findByFruitIdAndStartDate(fruit.getId(), delivery1.getDeliveryDate())).
                thenReturn(Optional.of(fruitPrice));

        // Вызов метода getReport
        DeliveryReportDto report = deliveryService.getDeliveryReport(startDate, endDate);

        // Проверка результата
        assertNotNull(report);
        assertEquals(1, report.getItems().size()); // Ожидаем 1 элемент в отчете

        // Проверка общей стоимости и веса для каждого элемента отчета
        assertEquals(100.0, report.getItems().get(0).getTotalCost());
        assertEquals(10.0, report.getItems().get(0).getTotalWeight());
    }
}
