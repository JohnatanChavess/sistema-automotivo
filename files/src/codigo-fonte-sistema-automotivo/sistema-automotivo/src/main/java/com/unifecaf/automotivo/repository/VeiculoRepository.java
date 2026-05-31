package com.unifecaf.automotivo.repository;

import com.unifecaf.automotivo.model.StatusVeiculo;
import com.unifecaf.automotivo.model.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * Repositório JPA para a entidade Veiculo.
 *
 * Estende JpaSpecificationExecutor para suportar filtros dinâmicos
 * combinados (ex: por marca + preço + status ao mesmo tempo).
 */
@Repository
public interface VeiculoRepository
        extends JpaRepository<Veiculo, Long>, JpaSpecificationExecutor<Veiculo> {

    // ── Filtros simples derivados do nome do método ──────────────────

    List<Veiculo> findByStatus(StatusVeiculo status);

    List<Veiculo> findByAnoFabricacao(Integer ano);

    List<Veiculo> findByModeloId(Long modeloId);

    List<Veiculo> findByModeloMarcaId(Long marcaId);

    // ── Filtro por faixa de preço (RF06) ─────────────────────────────

    List<Veiculo> findByPrecoBetween(BigDecimal precoMin, BigDecimal precoMax);

    // ── Query JPQL customizada — filtro combinado (RF05-RF08) ─────────

    /**
     * Busca veículos com múltiplos filtros opcionais simultâneos.
     * Parâmetros nulos são ignorados na cláusula WHERE.
     *
     * Exemplo de chamada:
     *   findWithFilters("Toyota", null, 2022, null, 50000.0, 150000.0)
     */
    @Query("""
        SELECT v FROM Veiculo v
        JOIN v.modelo m
        JOIN m.marca ma
        WHERE (:nomeMarca   IS NULL OR LOWER(ma.nome)  LIKE LOWER(CONCAT('%', :nomeMarca, '%')))
          AND (:nomeModelo  IS NULL OR LOWER(m.nome)   LIKE LOWER(CONCAT('%', :nomeModelo, '%')))
          AND (:ano         IS NULL OR v.anoFabricacao = :ano)
          AND (:status      IS NULL OR v.status = :status)
          AND (:precoMin    IS NULL OR v.preco >= :precoMin)
          AND (:precoMax    IS NULL OR v.preco <= :precoMax)
        ORDER BY v.preco ASC
    """)
    List<Veiculo> findWithFilters(
            @Param("nomeMarca")  String nomeMarca,
            @Param("nomeModelo") String nomeModelo,
            @Param("ano")        Integer ano,
            @Param("status")     StatusVeiculo status,
            @Param("precoMin")   BigDecimal precoMin,
            @Param("precoMax")   BigDecimal precoMax
    );

    // ── Contagem para relatório ───────────────────────────────────────

    long countByStatus(StatusVeiculo status);
}
