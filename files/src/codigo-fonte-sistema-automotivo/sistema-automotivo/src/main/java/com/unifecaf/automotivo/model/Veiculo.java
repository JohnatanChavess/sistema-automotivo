package com.unifecaf.automotivo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Entidade JPA que representa uma unidade física de VEÍCULO no estoque.
 *
 * POO aplicado:
 *  - Herança: estende VeiculoBase (classe abstrata com campos comuns)
 *  - Encapsulamento: todos os atributos são private
 *  - Associação N:1 com Modelo
 */
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

    /**
     * Validações de negócio (RNF04 — Bean Validation):
     *  - Ano não pode ser anterior a 1990 (regra do cliente - P8)
     *  - Ano não pode ser superior ao ano atual + 1
     */
    @NotNull(message = "O ano de fabricação é obrigatório.")
    @Min(value = 1990, message = "O ano de fabricação não pode ser anterior a 1990.")
    @Column(name = "ano_fabricacao", nullable = false)
    private Integer anoFabricacao;

    @NotBlank(message = "A cor é obrigatória.")
    @Size(max = 50)
    @Column(nullable = false)
    private String cor;

    /**
     * Preço com precisão decimal para valores monetários.
     */
    @NotNull(message = "O preço é obrigatório.")
    @DecimalMin(value = "0.01", message = "O preço deve ser maior que zero.")
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal preco;

    @NotNull(message = "A quilometragem é obrigatória.")
    @Min(value = 0, message = "A quilometragem não pode ser negativa.")
    @Column(nullable = false)
    private Integer quilometragem;

    /**
     * Status do veículo no estoque — usa Enum para garantir valores válidos.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private StatusVeiculo status = StatusVeiculo.DISPONIVEL;

    @Size(max = 500)
    private String observacoes;

    /**
     * Preenchido automaticamente na camada de serviço antes de salvar.
     */
    @Column(name = "data_cadastro", nullable = false)
    private LocalDate dataCadastro;

    /**
     * Relacionamento N:1 com Modelo.
     */
    @NotNull(message = "O modelo é obrigatório.")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "modelo_id", nullable = false)
    @JsonBackReference("modelo-veiculo")
    private Modelo modelo;

    /**
     * Implementação do método abstrato da classe pai.
     * Polimorfismo: o comportamento varia conforme o tipo de veículo.
     */
    @Override
    public String getTipoVeiculo() {
        return quilometragem == 0 ? "NOVO" : "USADO";
    }
}
