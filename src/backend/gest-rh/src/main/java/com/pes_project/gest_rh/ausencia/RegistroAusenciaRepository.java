package com.pes_project.gest_rh.ausencia;

import com.pes_project.gest_rh.ausencia.RegistroAusencia;
import com.pes_project.gest_rh.ausencia.StatusAusencia;
import com.pes_project.gest_rh.ausencia.TipoAusencia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface RegistroAusenciaRepository extends JpaRepository<RegistroAusencia, Long> {

    List<RegistroAusencia> findByFuncionarioIdOrderByDataInicioDesc(Long funcionarioId);

    long countByTipoAndDataInicioBetween(TipoAusencia tipo, LocalDate inicio, LocalDate fim);

    long countByTipoAndStatus(TipoAusencia tipo, StatusAusencia status);
}
