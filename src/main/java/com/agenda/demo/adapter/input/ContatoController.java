package com.agenda.demo.adapter.input;

import com.agenda.demo.core.app.dtos.ContatoDTO;
import com.agenda.demo.core.app.dtos.CriarContatoRequest;
import com.agenda.demo.core.app.dtos.CriarContatoResponse;
import com.agenda.demo.core.app.usecases.CriarContatoUseCase;
import com.agenda.demo.core.app.usecases.ListarContatosUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/contatos")
public class ContatoController {

    private final CriarContatoUseCase criarContatoUseCase;
    private final ListarContatosUseCase listarContatosUseCase;

    public ContatoController(CriarContatoUseCase criarContatoUseCase,
                             ListarContatosUseCase listarContatosUseCase) {
        this.criarContatoUseCase = criarContatoUseCase;
        this.listarContatosUseCase = listarContatosUseCase;
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
}