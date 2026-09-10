package com.pes_project.gest_rh.relatorio;

import com.pes_project.gest_rh.funcionario.dto.FuncionarioResponse;
import com.pes_project.gest_rh.funcionario.FuncionarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/relatorios")
@RequiredArgsConstructor
public class RelatorioController {

    private final FuncionarioService funcionarioService;

    // Retorna os dados já formatados pra o frontend gerar exportação (CSV/PDF fica pra Sprint 10)
    @GetMapping("/funcionarios")
    public ResponseEntity<List<FuncionarioResponse>> relatorioFuncionarios() {
        return ResponseEntity.ok(funcionarioService.listar(null));
    }
}
