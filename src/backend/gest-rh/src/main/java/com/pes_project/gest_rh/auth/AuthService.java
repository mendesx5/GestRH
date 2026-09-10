package com.pes_project.gest_rh.auth;

import com.pes_project.gest_rh.auth.dto.AuthResponse;
import com.pes_project.gest_rh.auth.dto.LoginRequest;
import com.pes_project.gest_rh.auth.dto.RegisterRequest;
import com.pes_project.gest_rh.auth.Usuario;
import com.pes_project.gest_rh.common.exception.BusinessException;
import com.pes_project.gest_rh.auth.UsuarioRepository;
import com.pes_project.gest_rh.common.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse registrar(RegisterRequest request) {
        if (usuarioRepository.existsByEmail(request.email())) {
            throw new BusinessException("Já existe um usuário com este e-mail", HttpStatus.CONFLICT);
        }

        Usuario usuario = Usuario.builder()
                .nome(request.nome())
                .email(request.email())
                .senha(passwordEncoder.encode(request.senha()))
                .perfil(request.perfil())
                .build();

        usuarioRepository.save(usuario);

        String token = jwtService.gerarToken(usuario);
        return new AuthResponse(token, usuario.getNome(), usuario.getEmail(), usuario.getPerfil());
    }

    public AuthResponse login(LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.email(), request.senha())
            );
        } catch (BadCredentialsException e) {
            throw new BusinessException("E-mail ou senha inválidos", HttpStatus.UNAUTHORIZED);
        }

        Usuario usuario = usuarioRepository.findByEmail(request.email())
                .orElseThrow(() -> new BusinessException("E-mail ou senha inválidos", HttpStatus.UNAUTHORIZED));

        String token = jwtService.gerarToken(usuario);
        return new AuthResponse(token, usuario.getNome(), usuario.getEmail(), usuario.getPerfil());
    }
}
