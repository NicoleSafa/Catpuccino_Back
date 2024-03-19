package com.example.catpuccino_back.models;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="producto", schema="catpuccino")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode

public class Producto {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        private Integer id;

        @Column(name="nombre")
        private String nombre;

        @Column(name="descripcion")
        private String descripcion;

        @Column(name="precio")
        private Integer precio;

}
