package com.agenda.demo.adapter.input;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class HealthCheckControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // Cria um "dublê" do KafkaTemplate para o Spring Context carregar em paz
    @MockitoBean
    private KafkaTemplate<String, String> kafkaTemplate;

    // Cria um "dublê" do ProducerFactory para o Spring Context carregar em paz
    @MockitoBean
    private ProducerFactory<String, String> producerFactory;

    @Test
    void deveRetornarStatusDaApi() throws Exception {
        mockMvc.perform(get("/api/status"))
                .andExpect(status().isOk())
                .andExpect(content().string("API da Agenda Atualizada 100% via CI/CD"));
    }
}