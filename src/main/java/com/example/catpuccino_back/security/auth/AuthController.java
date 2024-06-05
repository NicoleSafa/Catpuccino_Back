package com.example.catpuccino_back.security.auth;

import com.example.catpuccino_back.converter.UsuarioMapper;
import com.example.catpuccino_back.dto.LoginDTO;
import com.example.catpuccino_back.dto.UsuarioDTO;
import com.example.catpuccino_back.models.Usuario;
import com.example.catpuccino_back.repository.UsuarioRepository;
import com.example.catpuccino_back.security.service.AuthenticationService;
import com.example.catpuccino_back.security.service.JwtService;
import com.example.catpuccino_back.service.UsuarioService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.UnsupportedEncodingException;
import java.util.List;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private AuthenticationService authenticationService;

    private final JwtService jwtService;

    private final UsuarioService usuarioService;
    @Autowired
    private UsuarioRepository usuarioRepository;

    private final UsuarioMapper usuarioMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public AuthenticationResponseDTO register(@RequestBody UsuarioDTO usuarioDTO) {
        return authenticationService.register(usuarioDTO);
    }

    @PostMapping("/login")
    public AuthenticationResponseDTO register(@RequestBody LoginDTO loginDTO) {
        if (authenticationService.verifyPassword(loginDTO)) {
            return authenticationService.login(loginDTO);
        } else {
            return AuthenticationResponseDTO.builder().message("Invalid credentials").build();
        }
    }

    @PostMapping("/forgotPassword")
    public AuthenticationResponseDTO forgotPassordProcess(@RequestParam String email) throws MessagingException, UnsupportedEncodingException {

        boolean Mail = authenticationService.findByEmail(email);
        if (Mail == true) {
            //enviar correo
            authenticationService.sendEmail(email);

            return AuthenticationResponseDTO.builder().message("Correo enviado").username("1").build();
        } else {
            return AuthenticationResponseDTO.builder().message("Correo no registrado").username("2").build();
        }

    }

    @PostMapping("/setContra")
    public AuthenticationResponseDTO setContras(@RequestParam String token, @RequestParam String newPassword) {
        String username = jwtService.extractUsername(token);

        UsuarioDTO usuarioDTO = usuarioService.getByUsername(username);

        if (usuarioDTO != null) {

            usuarioDTO.setPassword(passwordEncoder.encode(newPassword));

            Usuario usuario = usuarioMapper.toEntity(usuarioDTO);

            usuarioRepository.save(usuario);

            return AuthenticationResponseDTO.builder().message("Cambio exitoso").build();
        } else {

            return AuthenticationResponseDTO.builder().message("Fallo al encriptar").build();
        }
    }

}
