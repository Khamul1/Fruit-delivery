package com.example.fruitdelivery;

import com.example.fruitdelivery.repository.FruitPriceMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FruitDeliveryApplication {

    public static void main(String[] args) {
        SpringApplication.run(FruitDeliveryApplication.class, args);
    }

    @Bean
    public FruitPriceMapper fruitPriceMapper() {
        return FruitPriceMapper.INSTANCE;
    }
}
