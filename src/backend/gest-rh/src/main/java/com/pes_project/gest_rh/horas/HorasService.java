package com.pes_project.gest_rh.horas;

import com.pes_project.gest_rh.funcionario.FuncionarioService;
import com.pes_project.gest_rh.horas.dto.HorasRequest;
import com.pes_project.gest_rh.horas.dto.HorasResponse;
import com.pes_project.gest_rh.horas.dto.SaldoHorasResponse;
import com.pes_project.gest_rh.funcionario.Funcionario;
import com.pes_project.gest_rh.horas.RegistroHoras;
import com.pes_project.gest_rh.horas.TipoHoras;
import com.pes_project.gest_rh.horas.RegistroHorasRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HorasService {

    private final RegistroHorasRepository horasRepository;
    private final FuncionarioService funcionarioService;

    public HorasResponse registrar(HorasRequest request) {
        Funcionario funcionario = funcionarioService.buscarEntidade(request.funcionarioId());

        RegistroHoras registro = RegistroHoras.builder()
                .funcionario(funcionario)
                .horas(request.horas())
                .tipo(request.tipo())
                .data(request.data())
                .build();

        return HorasResponse.from(horasRepository.save(registro));
    }

    public SaldoHorasResponse saldoPorFuncionario(Long funcionarioId) {
        List<RegistroHoras> registros = horasRepository.findByFuncionarioIdOrderByDataDesc(funcionarioId);

        BigDecimal saldo = registros.stream()
                .map(r -> r.getTipo() == TipoHoras.EXTRA ? r.getHoras() : r.getHoras().negate())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        List<HorasResponse> historico = registros.stream().map(HorasResponse::from).toList();

        return new SaldoHorasResponse(funcionarioId, saldo, historico);
    }
}
