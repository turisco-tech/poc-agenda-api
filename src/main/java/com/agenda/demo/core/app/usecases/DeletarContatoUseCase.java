package com.agenda.demo.core.app.usecases;

import com.agenda.demo.core.domain.ports.ContatoEventPublisher;
import com.agenda.demo.core.domain.repos.ContatoRepository;
import com.agenda.demo.core.domain.vos.ContatoDeletadoEvent;
import java.util.UUID;

public class DeletarContatoUseCase {

    private final ContatoRepository repository;
    private final ContatoEventPublisher eventPublisher; // Nossa nova porta de saída!

    public DeletarContatoUseCase(ContatoRepository repository, ContatoEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public void executar(UUID id) {
        var contatoOptional = repository.buscarPorId(id);

        if (contatoOptional.isEmpty()) return;
        var contato = contatoOptional.get();

        repository.deletar(contato.getId());

        // O Publisher avisa o ecossistema que o contato foi deletado!
        var event = new ContatoDeletadoEvent(contato.getId().toString(), contato.getEmail().valor());
        eventPublisher.publicarExclusao(event);
    }
}