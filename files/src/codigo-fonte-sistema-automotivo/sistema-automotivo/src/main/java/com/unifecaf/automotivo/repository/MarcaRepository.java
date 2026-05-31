package com.unifecaf.automotivo.repository;

import com.unifecaf.automotivo.model.Marca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface MarcaRepository extends JpaRepository<Marca, Long> {

    Optional<Marca> findByNomeIgnoreCase(String nome);

    boolean existsByNomeIgnoreCase(String nome);
}
