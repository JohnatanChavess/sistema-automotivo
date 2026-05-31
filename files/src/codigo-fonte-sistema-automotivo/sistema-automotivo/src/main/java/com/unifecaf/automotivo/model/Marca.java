package com.unifecaf.automotivo.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "marca")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Marca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome da marca é obrigatório.")
    @Size(max = 100, message = "O nome da marca deve ter no máximo 100 caracteres.")
    @Column(nullable = false, unique = true)
    private String nome;

    @Size(max = 50)
    @Column(name = "pais_origem")
    private String paisOrigem;
    @OneToMany(mappedBy = "marca", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    @Builder.Default
    private List<Modelo> modelos = new ArrayList<>();
}
