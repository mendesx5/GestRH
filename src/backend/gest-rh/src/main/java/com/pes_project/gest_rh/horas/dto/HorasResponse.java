package com.pes_project.gest_rh.horas.dto;

import com.pes_project.gest_rh.horas.RegistroHoras;
import com.pes_project.gest_rh.horas.TipoHoras;

import java.math.BigDecimal;
import java.time.LocalDate;

public record HorasResponse(
        Long id,
        Long funcionarioId,
        BigDecimal horas,
        TipoHoras tipo,
        LocalDate data
) {
    public static HorasResponse from(RegistroHoras r) {
        return new HorasResponse(r.getId(), r.getFuncionario().getId(), r.getHoras(), r.getTipo(), r.getData());
    }
}
