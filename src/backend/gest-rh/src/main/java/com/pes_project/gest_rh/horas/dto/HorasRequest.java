package com.pes_project.gest_rh.horas.dto;

import com.pes_project.gest_rh.horas.TipoHoras;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record HorasRequest(
        @NotNull(message = "Funcionário é obrigatório") Long funcionarioId,
        @NotNull @DecimalMin(value = "0.01", message = "Horas deve ser maior que zero") BigDecimal horas,
        @NotNull(message = "Tipo é obrigatório (EXTRA ou COMPENSADA)") TipoHoras tipo,
        @NotNull(message = "Data é obrigatória") LocalDate data
) {}
