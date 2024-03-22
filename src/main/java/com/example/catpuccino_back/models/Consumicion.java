package com.example.catpuccino_back.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="consumicion", schema="catpuccino")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Consumicion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name="cantidad")
    private Integer cantidad;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name="id_reserva")
    @JsonIgnore
    private Reserva id_reserva;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_producto")
    @JsonIgnore
    private Producto id_producto;





}
