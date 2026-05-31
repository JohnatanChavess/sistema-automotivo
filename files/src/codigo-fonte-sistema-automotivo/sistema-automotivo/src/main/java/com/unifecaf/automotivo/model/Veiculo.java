package com.unifecaf.automotivo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "veiculo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Veiculo extends VeiculoBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "O ano de fabricação é obrigatório.")
    @Min(value = 1990, message = "O ano de fabricação não pode ser anterior a 1990.")
    @Column(name = "ano_fabricacao", nullable = false)
    private Integer anoFabricacao;

    @NotBlank(message = "A cor é obrigatória.")
    @Size(max = 50)
    @Column(nullable = false)
    private String cor;


    @NotNull(message = "O preço é obrigatório.")
    @DecimalMin(value = "0.01", message = "O preço deve ser maior que zero.")
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal preco;

    @NotNull(message = "A quilometragem é obrigatória.")
    @Min(value = 0, message = "A quilometragem não pode ser negativa.")
    @Column(nullable = false)
    private Integer quilometragem;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private StatusVeiculo status = StatusVeiculo.DISPONIVEL;

    @Size(max = 500)
    private String observacoes;

    @Column(name = "data_cadastro", nullable = false)
    private LocalDate dataCadastro;

    @NotNull(message = "O modelo é obrigatório.")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "modelo_id", nullable = false)
    @JsonBackReference("modelo-veiculo")
    private Modelo modelo;

    @Override
    public String getTipoVeiculo() {
        return quilometragem == 0 ? "NOVO" : "USADO";
    }
}
