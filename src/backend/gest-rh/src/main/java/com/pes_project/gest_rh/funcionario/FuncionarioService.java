package com.pes_project.gest_rh.funcionario;

import com.pes_project.gest_rh.funcionario.dto.FuncionarioRequest;
import com.pes_project.gest_rh.funcionario.dto.FuncionarioResponse;
import com.pes_project.gest_rh.funcionario.Funcionario;
import com.pes_project.gest_rh.auth.Usuario;
import com.pes_project.gest_rh.common.exception.BusinessException;
import com.pes_project.gest_rh.funcionario.FuncionarioRepository;
import com.pes_project.gest_rh.auth.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;
    private final UsuarioRepository usuarioRepository;

    public FuncionarioResponse criar(FuncionarioRequest request) {
        Usuario usuario = null;
        if (request.usuarioId() != null) {
            usuario = usuarioRepository.findById(request.usuarioId())
                    .orElseThrow(() -> new BusinessException("Usuário vinculado não encontrado", HttpStatus.NOT_FOUND));
        }

        Funcionario funcionario = Funcionario.builder()
                .nome(request.nome())
                .cargo(request.cargo())
                .dataAdmissao(request.dataAdmissao())
                .ativo(true)
                .usuario(usuario)
                .build();

        return FuncionarioResponse.from(funcionarioRepository.save(funcionario));
    }

    public List<FuncionarioResponse> listar(String busca) {
        List<Funcionario> funcionarios;
        if (busca == null || busca.isBlank()) {
            funcionarios = funcionarioRepository.findAll();
        } else {
            funcionarios = funcionarioRepository
                    .findByNomeContainingIgnoreCaseOrCargoContainingIgnoreCase(busca, busca);
        }
        return funcionarios.stream().map(FuncionarioResponse::from).toList();
    }

    public FuncionarioResponse buscarPorId(Long id) {
        return FuncionarioResponse.from(buscarEntidade(id));
    }

    public FuncionarioResponse editar(Long id, FuncionarioRequest request) {
        Funcionario funcionario = buscarEntidade(id);
        funcionario.setNome(request.nome());
        funcionario.setCargo(request.cargo());
        funcionario.setDataAdmissao(request.dataAdmissao());
        return FuncionarioResponse.from(funcionarioRepository.save(funcionario));
    }

    public void inativar(Long id) {
        Funcionario funcionario = buscarEntidade(id);
        funcionario.setAtivo(false);
        funcionarioRepository.save(funcionario);
    }

    public Funcionario buscarEntidade(Long id) {
        return funcionarioRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Funcionário não encontrado", HttpStatus.NOT_FOUND));
    }
}
