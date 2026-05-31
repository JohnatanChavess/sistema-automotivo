package com.unifecaf.automotivo.repository;

import com.unifecaf.automotivo.model.Marca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositório JPA para a entidade Marca.
 *
 * POO aplicado — ABSTRAÇÃO:
 *  Ao estender JpaRepository, ganhamos automaticamente os métodos:
 *  save(), findById(), findAll(), deleteById(), count(), etc.
 *  Sem escrever uma linha de SQL ou implementar a interface manualmente.
 */
@Repository
public interface MarcaRepository extends JpaRepository<Marca, Long> {

    /**
     * Spring Data JPA deriva automaticamente o SQL a partir do nome do método.
     * Equivale a: SELECT * FROM marca WHERE nome = ?
     */
    Optional<Marca> findByNomeIgnoreCase(String nome);

    boolean existsByNomeIgnoreCase(String nome);
}
