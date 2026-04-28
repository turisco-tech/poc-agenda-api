package com.agenda.demo.core.app.usecases;

import com.agenda.demo.core.app.dtos.ContatoDTO;
import com.agenda.demo.core.domain.entities.Contato;
import com.agenda.demo.core.domain.repos.ContatoRepository;
import com.agenda.demo.core.domain.vos.Email;
import org.junit.jupiter.api.Test;

import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ListarContatosUseCaseTest {

    @Test
    void deveRetornarListaDeContatosDto() {
        // Arrange
        ContatoRepository fakeRepository = new ContatoRepository() {
            @Override
            public Contato salvar(Contato contato) {
                return null;
            }

            @Override
            public List<Contato> listarTodos() {
                return List.of(new Contato("Marcos", new Email("teste@teste.com")));
            }
        };
        ListarContatosUseCase useCase = new ListarContatosUseCase(fakeRepository);

        // Act
        List<ContatoDTO> resultado = useCase.executar();

        // Assert
        assertEquals(1, resultado.size());
        assertEquals("Marcos", resultado.get(0).nome());
    }
}