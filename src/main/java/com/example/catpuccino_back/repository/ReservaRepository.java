package com.example.catpuccino_back.repository;


import com.example.catpuccino_back.models.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Integer> {
    //TE PONE LA ÚLTIMA RESERVA LIGADA AL USUARIO
    @Query(value = "SELECT r.id " +
            "FROM catpuccino.usuario u " +
            "JOIN catpuccino.reserva r ON r.id_usuario = u.id " +
            "WHERE u.id = %:id " +
            "ORDER BY r.fecha DESC " +
            "LIMIT 1;", nativeQuery = true)
    Integer ultimareservausuario(int id);

}
