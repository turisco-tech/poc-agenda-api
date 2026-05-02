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

//    @Bean
//    public ProducerFactory<String, String> producerFactory() {
//        Map<String, Object> configProps = new HashMap<>();
//
//        // 1. Onde o Kafka está rodando?
//        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
//
//        // 2. Como serializar a Chave da mensagem? (Vamos usar String)
//        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//
//        // 3. Como serializar o Valor da mensagem? (Nosso JSON já foi convertido para String pelo ObjectMapper)
//        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//
//        return new DefaultKafkaProducerFactory<>(configProps);
//    }
//
//    @Bean
//    public KafkaTemplate<String, String> kafkaTemplate() {
//        return new KafkaTemplate<>(producerFactory());
//    }

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