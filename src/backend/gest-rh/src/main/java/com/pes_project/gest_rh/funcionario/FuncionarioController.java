package com.pes_project.gest_rh.funcionario;

import com.pes_project.gest_rh.funcionario.dto.FuncionarioRequest;
import com.pes_project.gest_rh.funcionario.dto.FuncionarioResponse;
import com.pes_project.gest_rh.funcionario.FuncionarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/funcionarios")
@RequiredArgsConstructor
public class FuncionarioController {

    private final FuncionarioService funcionarioService;

    @PostMapping
    public ResponseEntity<FuncionarioResponse> criar(@Valid @RequestBody FuncionarioRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(funcionarioService.criar(request));
    }

    @GetMapping
    public ResponseEntity<List<FuncionarioResponse>> listar(@RequestParam(required = false) String busca) {
        return ResponseEntity.ok(funcionarioService.listar(busca));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FuncionarioResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(funcionarioService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FuncionarioResponse> editar(@PathVariable Long id,
                                                        @Valid @RequestBody FuncionarioRequest request) {
        return ResponseEntity.ok(funcionarioService.editar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> inativar(@PathVariable Long id) {
        funcionarioService.inativar(id);
        return ResponseEntity.noContent().build();
    }
}
