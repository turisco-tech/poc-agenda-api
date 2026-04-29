package com.agenda.demo.core.app.usecases;

import com.agenda.demo.core.app.dtos.CriarContatoRequest;
import com.agenda.demo.core.app.dtos.CriarContatoResponse;
import com.agenda.demo.core.domain.entities.Contato;
import com.agenda.demo.core.domain.ports.ContatoEventPublisher;
import com.agenda.demo.core.domain.repos.ContatoRepository;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CriarContatoUseCaseTest {

    @Test
    void deveExecutarComSucessoERetornarResponse() {
        // 1. Arrange: Prepara o cenário com um repositório "Fake"
        ContatoRepository fakeRepository = new ContatoRepository() {
            @Override
            public Contato salvar(Contato contato) {
                return contato; // Apenas devolve o contato para fingir que salvou
            }

            @Override
            public List<Contato> listarTodos() {
                return List.of();
            }

            @Override
            public Optional<Contato> buscarPorId(UUID id) {
                return Optional.empty();
            }

            @Override
            public void deletar(UUID id) {

            }
        };

        // Cria um Publisher "Fake" que não faz nada (Dummy object)
        ContatoEventPublisher fakePublisher = contato -> {
            // No teste unitário, apenas fingimos que publicou
        };

        CriarContatoUseCase useCase = new CriarContatoUseCase(fakeRepository, fakePublisher);
        CriarContatoRequest request = new CriarContatoRequest("Marcos", "teste@teste.com");

        // 2. Act: Executa a ação
        CriarContatoResponse response = useCase.executar(request);

        // 3. Assert: Verifica se o resultado é o esperado
        assertNotNull(response.id());
        assertEquals("Marcos", response.nome());
        assertEquals("teste@teste.com", response.email());
    }
}