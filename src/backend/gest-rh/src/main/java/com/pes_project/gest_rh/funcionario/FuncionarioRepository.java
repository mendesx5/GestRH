package com.pes_project.gest_rh.funcionario;

import com.pes_project.gest_rh.funcionario.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

    List<Funcionario> findByNomeContainingIgnoreCaseOrCargoContainingIgnoreCase(String nome, String cargo);

    long countByAtivoTrue();
}
