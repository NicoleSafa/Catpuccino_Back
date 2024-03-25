package com.example.catpuccino_back.security.service;



import com.example.catpuccino_back.converter.UsuarioMapper;
import com.example.catpuccino_back.dto.LoginDTO;
import com.example.catpuccino_back.dto.UsuarioDTO;
import com.example.catpuccino_back.security.auth.AuthenticationResponseDTO;
import com.example.catpuccino_back.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {


    private final UsuarioService usuarioService;

    private final UsuarioMapper usuarioMapper;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;


    public AuthenticationResponseDTO register(UsuarioDTO usuarioDTO){
        usuarioDTO.setPassword(passwordEncoder.encode(usuarioDTO.getPassword()));
        UsuarioDTO dto = usuarioService.save(usuarioDTO);
        String token = jwtService.generateToken(usuarioMapper.toEntity(dto));
        return AuthenticationResponseDTO
                .builder()
                .token(token)
                .build();
    }

    public AuthenticationResponseDTO login(LoginDTO loginDTO){
        UsuarioDTO user = usuarioService.getByUsername(loginDTO.getNombreUsuario());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getNombreUsuario(),
                        loginDTO.getPassword(),
                        List.of(new SimpleGrantedAuthority(user.getRol().name()))
                )
        );
        String token = jwtService.generateToken(usuarioMapper.toEntity(user));
        return  AuthenticationResponseDTO
                .builder()
                .token(token)
                .message("Login success")
                .build();
    }

    public boolean verifyPassword(LoginDTO loginDTO){
        return usuarioService.existByCredentials(loginDTO.getNombreUsuario(),loginDTO.getPassword());

    }
}
