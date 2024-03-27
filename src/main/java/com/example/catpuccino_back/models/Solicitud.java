package com.example.catpuccino_back.models;

import com.example.catpuccino_back.models.enums.EstadoSolicitud;
import com.example.catpuccino_back.models.enums.Sexo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="solicitud", schema="catpuccino")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"idUsuario","idGato"})
public class Solicitud {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_usuario")
    @JsonIgnore
    private Usuario idUsuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_gato")
    @JsonIgnore
    private Gato idGato;

    @Column(name = "titulo")
    private String titulo;
    @Column(name = "mensaje")
    private String mensaje;

    @Column(name="estado")
    private EstadoSolicitud estadoSolicitud;
}
