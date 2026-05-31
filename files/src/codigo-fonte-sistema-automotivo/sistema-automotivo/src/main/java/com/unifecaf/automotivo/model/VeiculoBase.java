package com.unifecaf.automotivo.model;

import jakarta.persistence.MappedSuperclass;
import java.math.BigDecimal;

/**
 * Classe abstrata base para todos os tipos de veículos.
 *
 * POO aplicado — HERANÇA e ABSTRAÇÃO:
 *  - @MappedSuperclass: campos desta classe são mapeados nas tabelas das subclasses
 *  - getTipoVeiculo() é abstrato — cada subclasse implementa sua própria lógica (Polimorfismo)
 *  - Concentra atributos comuns, evitando repetição de código (princípio DRY)
 *
 * Hierarquia:
 *   VeiculoBase (abstrata)
 *       └── Veiculo (@Entity principal)
 *
 * Exemplo de extensão futura:
 *   VeiculoBase → VeiculoEletrico (adiciona autonomiaBateria)
 *   VeiculoBase → VeiculoHibrido  (adiciona tipoHibridizacao)
 */
@MappedSuperclass
public abstract class VeiculoBase {

    /**
     * Método abstrato que força cada subclasse a definir
     * o tipo do veículo — demonstra Polimorfismo.
     *
     * @return String representando o tipo ("NOVO" ou "USADO")
     */
    public abstract String getTipoVeiculo();

    /**
     * Método concreto compartilhado por todas as subclasses.
     * Demonstra Herança: subclasses herdam este comportamento gratuitamente.
     *
     * @param preco  preço do veículo
     * @param desconto percentual de desconto (ex: 0.10 = 10%)
     * @return preço com desconto aplicado
     */
    public BigDecimal calcularPrecoComDesconto(BigDecimal preco, double desconto) {
        if (desconto < 0 || desconto >= 1) {
            throw new IllegalArgumentException("Desconto inválido: deve ser entre 0 e 0.99");
        }
        return preco.multiply(BigDecimal.valueOf(1 - desconto));
    }
}
