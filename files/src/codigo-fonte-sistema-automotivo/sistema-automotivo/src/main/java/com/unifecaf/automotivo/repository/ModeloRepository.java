package com.unifecaf.automotivo.repository;

import com.unifecaf.automotivo.model.Modelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositório JPA para a entidade Modelo.
 */
@Repository
public interface ModeloRepository extends JpaRepository<Modelo, Long> {

    /**
     * Busca todos os modelos de uma marca específica.
     * SQL gerado: SELECT * FROM modelo WHERE marca_id = ?
     */
    List<Modelo> findByMarcaId(Long marcaId);

    /**
     * Busca modelos por categoria (ex: "SUV", "Sedan").
     */
    List<Modelo> findByCategoriaIgnoreCase(String categoria);
}
