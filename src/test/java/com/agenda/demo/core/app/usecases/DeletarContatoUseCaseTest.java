package com.agenda.demo.core.app.usecases;

import com.agenda.demo.core.domain.entities.Contato;
import com.agenda.demo.core.domain.ports.ContatoEventPublisher;
import com.agenda.demo.core.domain.repos.ContatoRepository;
import com.agenda.demo.core.domain.events.ContatoDeletadoEvent;
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
        final boolean[] eventoPublicado = {false};

        // Fake do Repository
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
        DeletarContatoUseCase useCase = getDeletarContatoUseCase(idParaDeletar, eventoPublicado, fakeRepository);

        // Act
        useCase.executar(idParaDeletar);

        // Assert
        assertTrue(deletadoNoBanco[0], "O método deletar do repositório deveria ter sido chamado com o ID correto");
        assertTrue(eventoPublicado[0], "O evento de exclusão deveria ter sido publicado com os dados corretos no publisher");
    }

    private static @NonNull DeletarContatoUseCase getDeletarContatoUseCase(UUID idParaDeletar,
                                                                           boolean[] eventoPublicado,
                                                                           ContatoRepository fakeRepository) {
        ContatoEventPublisher fakePublisher = new ContatoEventPublisher() {
            @Override
            public void publicarContatoCriado(Contato contato) {

            }

            @Override
            public void publicarExclusao(ContatoDeletadoEvent evento) {
                // Verificamos se o evento foi gerado com os dados corretos da entidade
                if (evento.id().equals(idParaDeletar.toString()) &&
                        evento.email().equals("joao@teste.com")) {
                    eventoPublicado[0] = true;
                }
            }

            // Se houver outros métodos na interface (como publicarCriacao), adicione-os aqui retornando vazio
        };

        // Instanciamos injetando as duas portas de saída!
        DeletarContatoUseCase useCase = new DeletarContatoUseCase(fakeRepository, fakePublisher);
        return useCase;
    }
}