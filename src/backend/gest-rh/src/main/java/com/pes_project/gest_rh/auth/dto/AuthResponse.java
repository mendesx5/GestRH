package com.pes_project.gest_rh.auth.dto;

import com.pes_project.gest_rh.auth.Perfil;

public record AuthResponse(
        String token,
        String nome,
        String email,
        Perfil perfil
) {}
