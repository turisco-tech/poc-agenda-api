package com.agenda.demo.adapter.input;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// 1. Trocamos @SpringBootTest por @WebMvcTest passando apenas o Controller alvo!
@WebMvcTest(HealthCheckController.class)
class HealthCheckControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // 2. Os "dublês" (@MockitoBean) do Kafka foram removidos porque o Spring
    // não vai nem tentar carregar a mensageria ou o banco de dados nesta fatia (slice).

    @Test
    void deveRetornarStatusDaApi() throws Exception {
        mockMvc.perform(get("/api/status"))
                .andExpect(status().isOk())
                .andExpect(content().string("API da Agenda Atualizada 100% via CI/CD"));
    }
}