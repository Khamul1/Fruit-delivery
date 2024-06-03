package com.example.fruitdelivery.test;

import com.example.fruitdelivery.controller.DeliveryController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DeliveryController.class)
class DeliveryControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(webApplicationContext).build();
    }

    @Test
    void createDelivery() throws Exception {
        // Заглушка для данных о поставке
        String deliveryJson = "{\n" +
                "  \"supplierId\": 1,\n" +
                "  \"deliveryDate\": \"2024-06-02\",\n" +
                "  \"items\": [\n" +
                "    {\n" +
                "      \"fruitId\": 1,\n" +
                "      \"quantity\": 10\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        mockMvc.perform(MockMvcRequestBuilders.post("/api/deliveries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(deliveryJson))
                .andExpect(status().isOk());
    }

    @Test
    void getReport() throws Exception {
        LocalDate startDate = LocalDate.of(2024, 6, 1);
        LocalDate endDate = LocalDate.of(2024, 6, 30);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/deliveries/report")
                        .param("startDate", startDate.toString())
                        .param("endDate", endDate.toString()))
                .andExpect(status().isOk());
    }
}
