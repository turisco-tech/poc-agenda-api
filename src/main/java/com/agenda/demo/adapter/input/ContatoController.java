package com.agenda.demo.adapter.input;

import com.agenda.demo.core.app.dtos.AtualizarContatoRequest;
import com.agenda.demo.core.app.dtos.ContatoDTO;
import com.agenda.demo.core.app.dtos.CriarContatoRequest;
import com.agenda.demo.core.app.dtos.CriarContatoResponse;
import com.agenda.demo.core.app.usecases.AtualizarContatoUseCase;
import com.agenda.demo.core.app.usecases.CriarContatoUseCase;
import com.agenda.demo.core.app.usecases.DeletarContatoUseCase;
import com.agenda.demo.core.app.usecases.ListarContatosUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*")// Libera o CORS para qualquer origem (Ideal para MVP/Dev)
@RequestMapping("/api/contatos")
public class ContatoController {

    private final CriarContatoUseCase criarContatoUseCase;
    private final ListarContatosUseCase listarContatosUseCase;
    private final AtualizarContatoUseCase atualizarContatoUseCase;
    private final DeletarContatoUseCase deletarContatoUseCase;

    public ContatoController(CriarContatoUseCase criarContatoUseCase,
                             ListarContatosUseCase listarContatosUseCase,
                             AtualizarContatoUseCase atualizarContatoUseCase,
                             DeletarContatoUseCase deletarContatoUseCase) {
        this.criarContatoUseCase = criarContatoUseCase;
        this.listarContatosUseCase = listarContatosUseCase;
        this.atualizarContatoUseCase = atualizarContatoUseCase;
        this.deletarContatoUseCase = deletarContatoUseCase;
    }

    @PostMapping
    public ResponseEntity<CriarContatoResponse> criar(@RequestBody CriarContatoRequest request) {
        CriarContatoResponse response = criarContatoUseCase.executar(request);

        // Retorna 201 Created com a URI do novo recurso e o corpo da resposta
        return ResponseEntity.created(URI.create("/api/contatos/" + response.id())).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ContatoDTO>> listar() {
        return ResponseEntity.ok(listarContatosUseCase.executar());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContatoDTO> atualizar(@PathVariable UUID id,
                                                @RequestBody AtualizarContatoRequest request) {
        return ResponseEntity.ok(atualizarContatoUseCase.executar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        deletarContatoUseCase.executar(id);
        return ResponseEntity.noContent().build();
    }
}