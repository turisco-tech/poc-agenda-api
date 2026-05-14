package com.agenda.demo.core.app.usecases;

import com.agenda.demo.core.app.dtos.AtualizarContatoRequest;
import com.agenda.demo.core.app.dtos.ContatoDTO;
import com.agenda.demo.core.domain.entities.Contato;
import com.agenda.demo.core.domain.repos.ContatoRepository;
import com.agenda.demo.core.domain.vos.Email;
import org.jspecify.annotations.NonNull;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

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
        AtualizarContatoUseCase useCase = getAtualizarContatoUseCase();
        AtualizarContatoRequest request = new AtualizarContatoRequest("Marcos", "novo@teste.com");

        var id = UUID.randomUUID();
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            useCase.executar(id, request);
        });

        var errorMessage = "Contato não encontrado para o ID: " + id;
        assertEquals(errorMessage, exception.getMessage());
    }

    private static @NonNull AtualizarContatoUseCase getAtualizarContatoUseCase() {
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

        return new AtualizarContatoUseCase(fakeRepository);
    }
}