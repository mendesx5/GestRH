package com.pes_project.gest_rh.dashboard;

import com.pes_project.gest_rh.ausencia.AusenciaService;
import com.pes_project.gest_rh.dashboard.dto.DashboardIndicadoresResponse;
import com.pes_project.gest_rh.funcionario.FuncionarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final FuncionarioRepository funcionarioRepository;
    private final AusenciaService ausenciaService;

    public DashboardIndicadoresResponse indicadores() {
        long totalFuncionarios = funcionarioRepository.countByAtivoTrue();
        long faltasMes = ausenciaService.contarFaltasNoMesAtual();
        long feriasPendentes = ausenciaService.contarFeriasPendentes();

        return new DashboardIndicadoresResponse(totalFuncionarios, faltasMes, feriasPendentes);
    }
}
