package com.agenda.demo.adapter.input;

import com.agenda.demo.core.app.dtos.AtualizarContatoRequest;
import com.agenda.demo.core.app.dtos.ContatoDTO;
import com.agenda.demo.core.app.dtos.CriarContatoRequest;
import com.agenda.demo.core.app.dtos.CriarContatoResponse;
import com.agenda.demo.core.app.usecases.AtualizarContatoUseCase;
import com.agenda.demo.core.app.usecases.CriarContatoUseCase;
import com.agenda.demo.core.app.usecases.DeletarContatoUseCase;
import com.agenda.demo.core.app.usecases.ListarContatosUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ContatoDTO>> listarTodos() {
        List<ContatoDTO> contatos = listarContatosUseCase.executar();
        return ResponseEntity.ok(contatos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContatoDTO> atualizar(@PathVariable UUID id, @RequestBody AtualizarContatoRequest request) {
        ContatoDTO response = atualizarContatoUseCase.executar(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        deletarContatoUseCase.executar(id);
        return ResponseEntity.noContent().build();
    }
}