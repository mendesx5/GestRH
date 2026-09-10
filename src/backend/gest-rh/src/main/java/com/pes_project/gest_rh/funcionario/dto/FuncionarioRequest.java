package com.pes_project.gest_rh.funcionario.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record FuncionarioRequest(
        @NotBlank(message = "Nome é obrigatório") String nome,
        @NotBlank(message = "Cargo é obrigatório") String cargo,
        @NotNull(message = "Data de admissão é obrigatória") LocalDate dataAdmissao,
        Long usuarioId
) {}
