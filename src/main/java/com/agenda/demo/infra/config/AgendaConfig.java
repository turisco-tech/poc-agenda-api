package com.agenda.demo.infra.config;

import com.agenda.demo.core.app.usecases.AtualizarContatoUseCase;
import com.agenda.demo.core.app.usecases.CriarContatoUseCase;
import com.agenda.demo.core.app.usecases.DeletarContatoUseCase;
import com.agenda.demo.core.app.usecases.ListarContatosUseCase;
import com.agenda.demo.core.domain.ports.ContatoEventPublisher;
import com.agenda.demo.core.domain.repos.ContatoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AgendaConfig {

    // 1. Injetamos o "Environment" para ler as propriedades de nuvem dinamicamente
    @Bean
    public org.springframework.kafka.core.ProducerFactory<String, String> producerFactory(org.springframework.core.env.Environment env) {
        java.util.Map<String, Object> configProps = new java.util.HashMap<>();

        // Conexão com o servidor (Lê do application.properties / AWS)
        configProps.put(org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                env.getProperty("spring.kafka.bootstrap-servers"));

        // Serializadores String
        configProps.put(org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                org.apache.kafka.common.serialization.StringSerializer.class);
        configProps.put(org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                org.apache.kafka.common.serialization.StringSerializer.class);

        // Autenticação na Confluent Cloud
        configProps.put("security.protocol", env.getProperty("spring.kafka.properties.security.protocol"));
        configProps.put("sasl.mechanism", env.getProperty("spring.kafka.properties.sasl.mechanism"));
        configProps.put("sasl.jaas.config", env.getProperty("spring.kafka.properties.sasl.jaas.config"));

        return new org.springframework.kafka.core.DefaultKafkaProducerFactory<>(configProps);
    }

    // 2. Criamos o Template tipado corretamente
    @Bean
    public org.springframework.kafka.core.KafkaTemplate<String, String> kafkaTemplate(org.springframework.core.env.Environment env) {
        return new org.springframework.kafka.core.KafkaTemplate<>(producerFactory(env));
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public CriarContatoUseCase criarContatoUseCase(
            ContatoRepository contatoRepository,
            ContatoEventPublisher contatoEventPublisher) {
        return new CriarContatoUseCase(contatoRepository, contatoEventPublisher);
    }

    @Bean
    public ListarContatosUseCase listarContatosUseCase(ContatoRepository repository) {
        return new ListarContatosUseCase(repository);
    }

    @Bean
    public AtualizarContatoUseCase atualizarContatoUseCase(ContatoRepository repository) {
        return new AtualizarContatoUseCase(repository);
    }

    @Bean
    public DeletarContatoUseCase deletarContatoUseCase(ContatoRepository repository) {
        return new DeletarContatoUseCase(repository);
    }

}