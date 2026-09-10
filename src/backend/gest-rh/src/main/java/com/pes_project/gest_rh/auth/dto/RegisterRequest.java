package com.pes_project.gest_rh.auth.dto;

import com.pes_project.gest_rh.auth.Perfil;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank(message = "Nome é obrigatório") String nome,
        @NotBlank @Email(message = "E-mail inválido") String email,
        @NotBlank @Size(min = 8, message = "Senha deve ter no mínimo 8 caracteres") String senha,
        @NotNull(message = "Perfil é obrigatório") Perfil perfil
) {}
