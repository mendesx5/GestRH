package com.pes_project.gest_rh.ausencia;

import com.pes_project.gest_rh.ausencia.dto.AusenciaRequest;
import com.pes_project.gest_rh.ausencia.dto.AusenciaResponse;
import com.pes_project.gest_rh.ausencia.AusenciaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ausencias")
@RequiredArgsConstructor
public class AusenciaController {

    private final AusenciaService ausenciaService;

    @PostMapping
    public ResponseEntity<AusenciaResponse> registrar(@Valid @RequestBody AusenciaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ausenciaService.registrar(request));
    }

    @GetMapping
    public ResponseEntity<List<AusenciaResponse>> listarTodas() {
        return ResponseEntity.ok(ausenciaService.listarTodas());
    }

    @GetMapping("/funcionario/{id}")
    public ResponseEntity<List<AusenciaResponse>> historicoPorFuncionario(@PathVariable Long id) {
        return ResponseEntity.ok(ausenciaService.historicoPorFuncionario(id));
    }
}
