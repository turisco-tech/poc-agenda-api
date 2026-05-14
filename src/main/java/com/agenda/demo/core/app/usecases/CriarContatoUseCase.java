package com.agenda.demo.core.app.usecases;

import com.agenda.demo.core.app.dtos.CriarContatoRequest;
import com.agenda.demo.core.app.dtos.CriarContatoResponse;
import com.agenda.demo.core.domain.entities.Contato;
import com.agenda.demo.core.domain.repos.ContatoRepository;
import com.agenda.demo.core.domain.vos.Email;

public class CriarContatoUseCase {

    private final ContatoRepository repository;

    public CriarContatoUseCase(ContatoRepository repository) {
        this.repository = repository;
    }

    public CriarContatoResponse executar(CriarContatoRequest request) {
        // 1. Instanciar VO e Entidades
        Email email = new Email(request.email());
        Contato novoContato = new Contato(request.nome(), email);

        // 2. Salvar (O nosso novo adaptador Postgres fará a magia do Outbox aqui dentro!)
        Contato contatoSalvo = repository.salvar(novoContato);

        // 3. Retornar o DTO
        return new CriarContatoResponse(
                contatoSalvo.getId(),
                contatoSalvo.getNome(),
                contatoSalvo.getEmail().valor()
        );
    }
}