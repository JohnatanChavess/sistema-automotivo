package com.unifecaf.automotivo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "modelo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Modelo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do modelo é obrigatório.")
    @Size(max = 100)
    @Column(nullable = false)
    private String nome;

    @Size(max = 50)
    private String categoria; 

    @NotNull(message = "A marca é obrigatória para o modelo.")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "marca_id", nullable = false)
    @JsonBackReference
    private Marca marca;

    @OneToMany(mappedBy = "modelo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference("modelo-veiculo")
    @Builder.Default
    private List<Veiculo> veiculos = new ArrayList<>();
}
