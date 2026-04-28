package com.agenda.demo.infra.config;

import com.agenda.demo.core.app.usecases.CriarContatoUseCase;
import com.agenda.demo.core.app.usecases.ListarContatosUseCase;
import com.agenda.demo.core.domain.repos.ContatoRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ContatoConfig {

    @Bean
    public CriarContatoUseCase criarContatoUseCase(ContatoRepository repository) {
        return new CriarContatoUseCase(repository);
    }

    @Bean
    public ListarContatosUseCase listarContatosUseCase(ContatoRepository repository) {
        return new ListarContatosUseCase(repository);
    }
}