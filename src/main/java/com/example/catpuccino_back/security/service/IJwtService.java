package com.example.catpuccino_back.security.service;

import com.example.catpuccino_back.models.Usuario;

public interface IJwtService {
    String extractUserName(String token);

    String generateToken(Usuario usuario);

    boolean isTokenValid(String token, Usuario usuario);
}
