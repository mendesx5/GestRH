package com.pes_project.gest_rh.ausencia.dto;

import com.pes_project.gest_rh.ausencia.RegistroAusencia;
import com.pes_project.gest_rh.ausencia.StatusAusencia;
import com.pes_project.gest_rh.ausencia.TipoAusencia;

import java.time.LocalDate;

public record AusenciaResponse(
        Long id,
        Long funcionarioId,
        String funcionarioNome,
        TipoAusencia tipo,
        LocalDate dataInicio,
        LocalDate dataFim,
        StatusAusencia status
) {
    public static AusenciaResponse from(RegistroAusencia r) {
        return new AusenciaResponse(
                r.getId(),
                r.getFuncionario().getId(),
                r.getFuncionario().getNome(),
                r.getTipo(),
                r.getDataInicio(),
                r.getDataFim(),
                r.getStatus()
        );
    }
}
