package com.example.catpuccino_back.repository;

import com.example.catpuccino_back.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findTopByNombreUsuario(String nombreUsuario);

}
