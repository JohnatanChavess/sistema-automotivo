package com.unifecaf.automotivo.model;

/**
 * Enum que representa os possíveis estados de um veículo no estoque.
 * Uso de enum garante que apenas valores válidos sejam persistidos (RNF04).
 */
public enum StatusVeiculo {
    DISPONIVEL,  // Veículo pronto para venda
    VENDIDO,     // Negociação concluída
    RESERVADO    // Aguardando conclusão de negociação
}
