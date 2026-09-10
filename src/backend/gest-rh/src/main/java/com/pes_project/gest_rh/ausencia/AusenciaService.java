package com.pes_project.gest_rh.ausencia;

import com.pes_project.gest_rh.ausencia.dto.AusenciaRequest;
import com.pes_project.gest_rh.ausencia.dto.AusenciaResponse;
import com.pes_project.gest_rh.funcionario.Funcionario;
import com.pes_project.gest_rh.ausencia.RegistroAusencia;
import com.pes_project.gest_rh.ausencia.TipoAusencia;
import com.pes_project.gest_rh.common.exception.BusinessException;
import com.pes_project.gest_rh.ausencia.RegistroAusenciaRepository;
import com.pes_project.gest_rh.funcionario.FuncionarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AusenciaService {

    private final RegistroAusenciaRepository ausenciaRepository;
    private final FuncionarioService funcionarioService;

    public AusenciaResponse registrar(AusenciaRequest request) {
        if (request.dataFim().isBefore(request.dataInicio())) {
            throw new BusinessException("Data de fim não pode ser anterior à data de início", HttpStatus.BAD_REQUEST);
        }

        Funcionario funcionario = funcionarioService.buscarEntidade(request.funcionarioId());

        RegistroAusencia registro = RegistroAusencia.builder()
                .funcionario(funcionario)
                .tipo(request.tipo())
                .dataInicio(request.dataInicio())
                .dataFim(request.dataFim())
                .build();

        return AusenciaResponse.from(ausenciaRepository.save(registro));
    }

    public List<AusenciaResponse> historicoPorFuncionario(Long funcionarioId) {
        return ausenciaRepository.findByFuncionarioIdOrderByDataInicioDesc(funcionarioId)
                .stream().map(AusenciaResponse::from).toList();
    }

    public List<AusenciaResponse> listarTodas() {
        return ausenciaRepository.findAll().stream().map(AusenciaResponse::from).toList();
    }

    public long contarFaltasNoMesAtual() {
        var hoje = java.time.LocalDate.now();
        var inicioMes = hoje.withDayOfMonth(1);
        var fimMes = hoje.withDayOfMonth(hoje.lengthOfMonth());
        return ausenciaRepository.countByTipoAndDataInicioBetween(TipoAusencia.FALTA, inicioMes, fimMes);
    }

    public long contarFeriasPendentes() {
        return ausenciaRepository.countByTipoAndStatus(
                TipoAusencia.FERIAS, com.pes_project.gest_rh.ausencia.StatusAusencia.PENDENTE);
    }
}
