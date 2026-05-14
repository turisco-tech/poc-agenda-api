package com.agenda.demo.core.app.usecases;

import com.agenda.demo.core.domain.entities.Contato;
import com.agenda.demo.core.domain.repos.ContatoRepository;
import com.agenda.demo.core.domain.vos.Email;
import org.jspecify.annotations.NonNull;
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

        // 1. Precisamos de um Contato válido para o Use Case não parar no "isEmpty()"
        // Ajuste os parâmetros do construtor de acordo com a sua classe de domínio!
        Email emailFake = new Email("joao@teste.com");
        Contato contatoMock = new Contato(idParaDeletar, "João", emailFake);

        final boolean[] deletadoNoBanco = {false};

        // Fake do Repository
        DeletarContatoUseCase useCase = getDeletarContatoUseCase(idParaDeletar, contatoMock, deletadoNoBanco);

        // Act
        useCase.executar(idParaDeletar);

        // Assert
        assertTrue(deletadoNoBanco[0], "O método deletar do repositório deveria ter sido chamado com o ID correto");
    }

    private static @NonNull DeletarContatoUseCase getDeletarContatoUseCase(UUID idParaDeletar, Contato contatoMock, boolean[] deletadoNoBanco) {
        ContatoRepository fakeRepository = new ContatoRepository() {
            @Override
            public Optional<Contato> buscarPorId(UUID id) {
                if (id.equals(idParaDeletar)) {
                    return Optional.of(contatoMock); // Agora ele encontra o contato!
                }
                return Optional.empty();
            }

            @Override
            public void deletar(UUID id) {
                if (id.equals(idParaDeletar)) {
                    deletadoNoBanco[0] = true;
                }
            }

            @Override
            public Contato salvar(Contato contato) {
                return null;
            }

            @Override
            public List<Contato> listarTodos() {
                return null;
            }
        };

        // Fake do Publisher
        return getDeletarContatoUseCase(fakeRepository);
    }

    private static @NonNull DeletarContatoUseCase getDeletarContatoUseCase(ContatoRepository fakeRepository) {
        // Instanciamos injetando as duas portas de saída!
        return new DeletarContatoUseCase(fakeRepository);
    }
}