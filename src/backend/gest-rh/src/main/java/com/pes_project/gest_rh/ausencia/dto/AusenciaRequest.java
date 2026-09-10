package com.pes_project.gest_rh.ausencia.dto;

import com.pes_project.gest_rh.ausencia.TipoAusencia;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record AusenciaRequest(
        @NotNull(message = "Funcionário é obrigatório") Long funcionarioId,
        @NotNull(message = "Tipo é obrigatório (FALTA ou FERIAS)") TipoAusencia tipo,
        @NotNull(message = "Data de início é obrigatória") LocalDate dataInicio,
        @NotNull(message = "Data de fim é obrigatória") LocalDate dataFim
) {}
