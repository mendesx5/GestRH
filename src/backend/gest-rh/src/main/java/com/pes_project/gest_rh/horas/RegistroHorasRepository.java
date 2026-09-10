package com.pes_project.gest_rh.horas;

import com.pes_project.gest_rh.horas.RegistroHoras;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegistroHorasRepository extends JpaRepository<RegistroHoras, Long> {
    List<RegistroHoras> findByFuncionarioIdOrderByDataDesc(Long funcionarioId);
}
