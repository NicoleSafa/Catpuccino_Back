package com.example.catpuccino_back.models;

import com.example.catpuccino_back.models.enums.Rol;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name="usuario", schema="catpuccino")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name="nombre")
    private String nombre;

    @Column(name="apellidos")
    private String apellidos;

    @Column(name="telefono")
    private Integer telefono;

    @Column(name="email")
    private String email;

    @Column(name="dni")
    private String dni;

    @Column(name="rol")
    @Enumerated(EnumType.ORDINAL)
    private Rol rol;

    @Column(name="nombre_usuario")
    private String nombreUsuario;

    @Column(name="password")
    private String password;

    @OneToMany(mappedBy = "id_usuario", fetch = FetchType.LAZY)
    private Set<Reserva> reservas;

    @OneToMany(mappedBy = "idUsuario", fetch = FetchType.LAZY)
    private Set<Adopcion> adopciones;

    @OneToMany(mappedBy = "idUsuario", fetch = FetchType.LAZY)
    private Set<Solicitud> solicitudes;


}
