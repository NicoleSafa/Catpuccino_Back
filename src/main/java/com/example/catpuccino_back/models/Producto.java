package com.example.catpuccino_back.models;
import com.example.catpuccino_back.models.enums.Tipo;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CollectionId;

import java.util.Set;

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
        private Double precio;

        @Column(name="tipo")
        private Tipo tipo;

        @Column (name="imagen")
        private String imagen;

        @OneToMany(mappedBy = "id_producto")
        private Set<Consumicion> consumicion;


}
