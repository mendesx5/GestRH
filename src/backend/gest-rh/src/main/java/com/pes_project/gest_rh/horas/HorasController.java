package com.pes_project.gest_rh.horas;

import com.pes_project.gest_rh.horas.dto.HorasRequest;
import com.pes_project.gest_rh.horas.dto.HorasResponse;
import com.pes_project.gest_rh.horas.dto.SaldoHorasResponse;
import com.pes_project.gest_rh.horas.HorasService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/banco-horas")
@RequiredArgsConstructor
public class HorasController {

    private final HorasService horasService;

    @PostMapping
    public ResponseEntity<HorasResponse> registrar(@Valid @RequestBody HorasRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(horasService.registrar(request));
    }

    @GetMapping("/funcionario/{id}")
    public ResponseEntity<SaldoHorasResponse> saldo(@PathVariable Long id) {
        return ResponseEntity.ok(horasService.saldoPorFuncionario(id));
    }
}
