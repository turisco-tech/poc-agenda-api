package com.agenda.demo.core.app.usecases;

import com.agenda.demo.core.app.dtos.CriarContatoRequest;
import com.agenda.demo.core.app.dtos.CriarContatoResponse;
import com.agenda.demo.core.domain.entities.Contato;
import com.agenda.demo.core.domain.ports.ContatoEventPublisher;
import com.agenda.demo.core.domain.repos.ContatoRepository;
import com.agenda.demo.core.domain.vos.Email;

public class CriarContatoUseCase {

    private final ContatoRepository repository;
    private final ContatoEventPublisher eventPublisher; // Nossa nova porta de saída!

    // Injetamos as duas interfaces via construtor
    public CriarContatoUseCase(ContatoRepository repository, ContatoEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public CriarContatoResponse executar(CriarContatoRequest request) {
        // 1. Instanciar Value Objects e Entidades (As regras de negócio validam os dados aqui)
        Email email = new Email(request.email());
        Contato novoContato = new Contato(request.nome(), email);

        // 2. Salvar usando o contrato (Não sabemos se é SQL, NoSQL ou memória, e não importa!)
        Contato contatoSalvo = repository.salvar(novoContato);

        // 3. O Publisher avisa o ecossistema que o contato foi criado!
        eventPublisher.publicarContatoCriado(contatoSalvo);

        // 4. Retornar um DTO puro, protegendo a Entidade do Domínio
        return new CriarContatoResponse(
                contatoSalvo.getId(),
                contatoSalvo.getNome(),
                contatoSalvo.getEmail().valor()
        );
    }
}