package com.pes_project.gest_rh.horas.dto;

import java.math.BigDecimal;
import java.util.List;

public record SaldoHorasResponse(
        Long funcionarioId,
        BigDecimal saldo,
        List<HorasResponse> historico
) {}
