package com.example.catpuccino_back.models;

import com.example.catpuccino_back.models.enums.Raza;
import com.example.catpuccino_back.models.enums.Sexo;
import com.example.catpuccino_back.models.enums.Tamanyo;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name="gato", schema="catpuccino")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Gato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name="nombre")
    private String nombre;

    @Column(name="imagen")
    private String imagen;

    @Column(name="raza")
    private Raza raza;

    @Column(name="tamanyo")
    private Tamanyo tamanyo;

    @Column(name="sexo")
    private Sexo sexo;

    @Column(name="descripcion_corta")
    private String descripcionCorta;

    @Column(name="disponible")
    private Boolean disponible;

    @Column(name="vacunacion_completa")
    private Boolean vacunacionCompleta;

    @Column(name="chip")
    private Boolean chip;

    @Column(name="descripcion_larga")
    private String descripcionLarga;

    @Column(name="necesidades_especiales")
    private Boolean necesidadesEspeciales;

    @OneToMany(mappedBy = "idGato", fetch = FetchType.LAZY)
    private Set<Adopcion> adopciones;

    @OneToMany(mappedBy = "idGato", fetch = FetchType.LAZY)
    private Set<Solicitud> solicitudes;
}