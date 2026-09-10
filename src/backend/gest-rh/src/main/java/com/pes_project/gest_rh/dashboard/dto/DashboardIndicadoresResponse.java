package com.pes_project.gest_rh.dashboard.dto;

public record DashboardIndicadoresResponse(
        long totalFuncionarios,
        long faltasMes,
        long feriasPendentes
) {}
