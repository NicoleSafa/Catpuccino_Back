package com.example.catpuccino_back.security.auth;

import com.example.catpuccino_back.models.enums.Rol;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponseDTO {

    private String token;
    private String message;
    private Integer id;
    private Rol rol;
    private String username;
}
