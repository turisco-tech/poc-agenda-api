package com.agenda.demo.core.app.usecases;

import com.agenda.demo.core.domain.entities.Contato;
import com.agenda.demo.core.domain.repos.ContatoRepository;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;

class DeletarContatoUseCaseTest {

    @Test
    void deveDeletarContatoComSucesso() {
        // Arrange
        UUID idParaDeletar = UUID.randomUUID();
        final boolean[] deletado = {false}; // Flag para verificar se o método do banco foi chamado

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
            public Optional<Contato> buscarPorId(UUID id) {
                return Optional.empty();
            }

            @Override
            public void deletar(UUID id) {
                if (id.equals(idParaDeletar)) {
                    deletado[0] = true;
                }
            }
        };

        DeletarContatoUseCase useCase = new DeletarContatoUseCase(fakeRepository);

        // Act
        useCase.executar(idParaDeletar);

        // Assert
        assertTrue(deletado[0], "O método deletar do repositório deveria ter sido chamado com o ID correto");
    }
}