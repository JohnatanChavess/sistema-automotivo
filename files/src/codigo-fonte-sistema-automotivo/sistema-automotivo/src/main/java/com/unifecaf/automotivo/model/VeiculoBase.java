package com.unifecaf.automotivo.model;

import jakarta.persistence.MappedSuperclass;
import java.math.BigDecimal;


@MappedSuperclass
public abstract class VeiculoBase {

    public abstract String getTipoVeiculo();

    public BigDecimal calcularPrecoComDesconto(BigDecimal preco, double desconto) {
        if (desconto < 0 || desconto >= 1) {
            throw new IllegalArgumentException("Desconto inválido: deve ser entre 0 e 0.99");
        }
        return preco.multiply(BigDecimal.valueOf(1 - desconto));
    }
}
