package com.agenda.demo.core.app.usecases;

import com.agenda.demo.core.app.dtos.CriarContatoRequest;
import com.agenda.demo.core.app.dtos.CriarContatoResponse;
import com.agenda.demo.core.domain.entities.Contato;
import com.agenda.demo.core.domain.repos.ContatoRepository;
import org.junit.jupiter.api.Test;
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
        };
        CriarContatoUseCase useCase = new CriarContatoUseCase(fakeRepository);
        CriarContatoRequest request = new CriarContatoRequest("Marcos", "teste@teste.com");

        // 2. Act: Executa a ação
        CriarContatoResponse response = useCase.executar(request);

        // 3. Assert: Verifica se o resultado é o esperado
        assertNotNull(response.id());
        assertEquals("Marcos", response.nome());
        assertEquals("teste@teste.com", response.email());
    }
}