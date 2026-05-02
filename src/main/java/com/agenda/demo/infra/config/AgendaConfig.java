package com.agenda.demo.infra.config;

import com.agenda.demo.core.app.usecases.AtualizarContatoUseCase;
import com.agenda.demo.core.app.usecases.CriarContatoUseCase;
import com.agenda.demo.core.app.usecases.DeletarContatoUseCase;
import com.agenda.demo.core.app.usecases.ListarContatosUseCase;
import com.agenda.demo.core.domain.ports.ContatoEventPublisher;
import com.agenda.demo.core.domain.repos.ContatoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import java.util.HashMap;

@Configuration
public class AgendaConfig {

    // 1. Injetamos o "Environment" para ler as propriedades de nuvem dinamicamente
    @Bean
    public ProducerFactory<String, String> producerFactory(Environment env) {
        java.util.Map<String, Object> configProps = new HashMap<>();

        // Conexão com o servidor (Lê do application.properties / AWS)
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, env.getProperty("spring.kafka.bootstrap-servers"));

        // Serializadores String
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        // Autenticação na Confluent Cloud
        configProps.put("security.protocol", env.getProperty("spring.kafka.properties.security.protocol"));
        configProps.put("sasl.mechanism", env.getProperty("spring.kafka.properties.sasl.mechanism"));
        configProps.put("sasl.jaas.config", env.getProperty("spring.kafka.properties.sasl.jaas.config"));

        return new DefaultKafkaProducerFactory<>(configProps);
    }

    // 2. Criamos o Template tipado corretamente
    @Bean
    public KafkaTemplate<String, String> kafkaTemplate(Environment env) {
        return new KafkaTemplate<>(producerFactory(env));
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