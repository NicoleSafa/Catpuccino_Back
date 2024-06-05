package com.example.catpuccino_back.security.service;



import com.example.catpuccino_back.converter.UsuarioMapper;
import com.example.catpuccino_back.dto.LoginDTO;
import com.example.catpuccino_back.dto.UsuarioDTO;
import com.example.catpuccino_back.models.Usuario;
import com.example.catpuccino_back.repository.UsuarioRepository;
import com.example.catpuccino_back.security.auth.AuthenticationResponseDTO;
import com.example.catpuccino_back.service.UsuarioService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {


    private final UsuarioService usuarioService;

    private final UsuarioRepository usuarioRepository;

    private final UsuarioMapper usuarioMapper;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    @Autowired
    JavaMailSender javaMailSender;


    public AuthenticationResponseDTO register(UsuarioDTO usuarioDTO) {

        String usuario = usuarioDTO.getNombreUsuario();
        String email = usuarioDTO.getEmail();

        if (usuarioRepository.existsByEmail(email)) {

            String message = "El email " + email + " ya está registrado";

            return AuthenticationResponseDTO
                    .builder()
                    .message(message)
                    .username("error 1")
                    .build();

        }
        if (usuarioRepository.existsByUsuario(usuario)) {
            String message = "El usuario " + usuario + " ya está registrado";

            return AuthenticationResponseDTO
                    .builder()
                    .message(message)
                    .username("error 2")
                    .build();
        }

        usuarioDTO.setPassword(passwordEncoder.encode(usuarioDTO.getPassword()));
        UsuarioDTO dto = usuarioService.save(usuarioDTO);
        String token = jwtService.generateToken(usuarioMapper.toEntity(dto));
        return AuthenticationResponseDTO
                .builder()
                .token(token)
                .build();
    }

    public AuthenticationResponseDTO login(LoginDTO loginDTO) {
        UsuarioDTO user = usuarioService.getByUsername(loginDTO.getNombreUsuario());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getNombreUsuario(),
                        loginDTO.getPassword(),
                        List.of(new SimpleGrantedAuthority(user.getRol().name()))
                )
        );
        String token = jwtService.generateToken(usuarioMapper.toEntity(user));
        return AuthenticationResponseDTO
                .builder()
                .token(token)
                .id(user.getId())
                .rol(user.getRol())
                .username(user.getNombreUsuario())
                .message("Login success")
                .build();
    }

    public boolean verifyPassword(LoginDTO loginDTO) {
        return usuarioService.existByCredentials(loginDTO.getNombreUsuario(), loginDTO.getPassword());

    }

    public boolean findByEmail(String email) {

        if (usuarioRepository.existsByEmail(email)) {

            return true;

        } else {
            return false;
        }
    }

    public String sendEmail(String email) throws MessagingException, UnsupportedEncodingException {

        UsuarioDTO dto = usuarioService.save(usuarioRepository.findByEmail(email));
        String token = jwtService.generateToken(usuarioMapper.toEntity(dto));


        String subject = "Email Verification";
        String senderName = "Recuperación de contraseña";
        String mailContent = "<p> Hola "+ dto.getNombreUsuario() +", </p>"+
                "<p>Gracias por confiar en nosotros.</p>"+
                "Por favor haga click en el enlace para tener su nueva contraseña.</p><br>"+
                "<a href=\"http://localhost:3000/Recuperar?token="+token+"\">Cambiar Contraseña.</a><br>"+
                "<p> Atte. El equipo de Catpuccino.</p>";
        MimeMessage message = javaMailSender.createMimeMessage();
        var messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom("catpuccino29@gmail.com", senderName);
        messageHelper.setTo(email);
        messageHelper.setSubject(subject);
        messageHelper.setText(mailContent, true);
        javaMailSender.send(message);
        return email;
    }

}
