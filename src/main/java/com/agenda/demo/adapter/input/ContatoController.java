package com.agenda.demo.adapter.input;

import com.agenda.demo.core.app.dtos.CriarContatoRequest;
import com.agenda.demo.core.app.dtos.CriarContatoResponse;
import com.agenda.demo.core.app.usecases.CriarContatoUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.net.URI;

@RestController
@RequestMapping("/api/contatos")
public class ContatoController {

    private final CriarContatoUseCase criarContatoUseCase;

    public ContatoController(CriarContatoUseCase criarContatoUseCase) {
        this.criarContatoUseCase = criarContatoUseCase;
    }

    @PostMapping
    public ResponseEntity<CriarContatoResponse> criar(@RequestBody CriarContatoRequest request) {
        CriarContatoResponse response = criarContatoUseCase.executar(request);

        // Retorna 201 Created com a URI do novo recurso e o corpo da resposta
        return ResponseEntity.created(URI.create("/api/contatos/" + response.id())).body(response);
    }
}