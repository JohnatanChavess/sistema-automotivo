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


@Repository
public interface VeiculoRepository
        extends JpaRepository<Veiculo, Long>, JpaSpecificationExecutor<Veiculo> {

    List<Veiculo> findByStatus(StatusVeiculo status);

    List<Veiculo> findByAnoFabricacao(Integer ano);

    List<Veiculo> findByModeloId(Long modeloId);

    List<Veiculo> findByModeloMarcaId(Long marcaId);


    List<Veiculo> findByPrecoBetween(BigDecimal precoMin, BigDecimal precoMax);

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


    long countByStatus(StatusVeiculo status);
}
