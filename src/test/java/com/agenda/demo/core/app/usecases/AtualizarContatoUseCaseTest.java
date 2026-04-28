package com.agenda.demo.core.app.usecases;

import com.agenda.demo.core.app.dtos.AtualizarContatoRequest;
import com.agenda.demo.core.app.dtos.ContatoDTO;
import com.agenda.demo.core.domain.entities.Contato;
import com.agenda.demo.core.domain.repos.ContatoRepository;
import com.agenda.demo.core.domain.vos.Email;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AtualizarContatoUseCaseTest {

    @Test
    void deveAtualizarEmailDoContatoComSucesso() {
        // Arrange (Preparar)
        UUID idExistente = UUID.randomUUID();
        Contato contatoExistente = new Contato(idExistente, "Marcos", new Email("antigo@teste.com"));

        ContatoRepository fakeRepository = new ContatoRepository() {
            @Override
            public Contato salvar(Contato contato) {
                return contato;
            }

            @Override
            public List<Contato> listarTodos() {
                return null;
            }

            @Override
            public void deletar(UUID id) {
            }

            @Override
            public Optional<Contato> buscarPorId(UUID id) {
                // Simula que achou o contato no banco
                return id.equals(idExistente) ? Optional.of(contatoExistente) : Optional.empty();
            }
        };

        AtualizarContatoUseCase useCase = new AtualizarContatoUseCase(fakeRepository);
        AtualizarContatoRequest request = new AtualizarContatoRequest("Marcos", "novo@teste.com");

        // Act (Agir)
        ContatoDTO resultado = useCase.executar(idExistente, request);

        // Assert (Verificar)
        assertNotNull(resultado);
        assertEquals(idExistente, resultado.id());
        assertEquals("novo@teste.com", resultado.email());
    }

    @Test
    void deveLancarExcecaoQuandoContatoNaoExistir() {
        // Arrange
        ContatoRepository fakeRepository = new ContatoRepository() {
            @Override
            public Contato salvar(Contato contato) {
                return null;
            }

            @Override
            public List<Contato> listarTodos() {
                return null;
            }

            @Override
            public void deletar(UUID id) {
            }

            @Override
            public Optional<Contato> buscarPorId(UUID id) {
                // Simula banco de dados vazio
                return Optional.empty();
            }
        };

        AtualizarContatoUseCase useCase = new AtualizarContatoUseCase(fakeRepository);
        AtualizarContatoRequest request = new AtualizarContatoRequest("Marcos", "novo@teste.com");

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            useCase.executar(UUID.randomUUID(), request);
        });

        assertEquals("Contato não encontrado", exception.getMessage());
    }
}