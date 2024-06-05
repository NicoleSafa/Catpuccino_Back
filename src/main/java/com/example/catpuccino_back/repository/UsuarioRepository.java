package com.example.catpuccino_back.repository;

import com.example.catpuccino_back.dto.UsuarioDTO;
import com.example.catpuccino_back.models.Gato;
import com.example.catpuccino_back.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findTopByNombreUsuario(String nombreUsuario);


    @Query(value = "select case when count(u) > 0 then true else false end from catpuccino.usuario u where u.email = :email", nativeQuery = true)
    boolean existsByEmail(String email);

    UsuarioDTO findByEmail(String email);
    @Query(value = "select case when count(u) > 0 then true else false end from catpuccino.usuario u where u.nombre_usuario = :usuario", nativeQuery = true)
    boolean existsByUsuario(String usuario);
}
