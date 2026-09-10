package com.pes_project.gest_rh.funcionario.dto;

import com.pes_project.gest_rh.funcionario.Funcionario;

import java.time.LocalDate;

public record FuncionarioResponse(
        Long id,
        String nome,
        String cargo,
        LocalDate dataAdmissao,
        boolean ativo
) {
    public static FuncionarioResponse from(Funcionario f) {
        return new FuncionarioResponse(f.getId(), f.getNome(), f.getCargo(), f.getDataAdmissao(), f.isAtivo());
    }
}
