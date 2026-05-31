package com.unifecaf.automotivo.service;

import com.unifecaf.automotivo.exception.RecursoNaoEncontradoException;
import com.unifecaf.automotivo.model.Marca;
import com.unifecaf.automotivo.repository.MarcaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Camada de Serviço para operações de MARCA.
 *
 * POO aplicado — ABSTRAÇÃO:
 *  O Controller não conhece detalhes do banco de dados.
 *  Toda regra de negócio fica aqui, separada da camada de apresentação.
 *
 * @RequiredArgsConstructor (Lombok) injeta automaticamente o repository via construtor.
 */
@Service
@RequiredArgsConstructor
public class MarcaService {

    private final MarcaRepository marcaRepository;

    public List<Marca> listarTodas() {
        return marcaRepository.findAll();
    }

    public Marca buscarPorId(Long id) {
        return marcaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Marca", id));
    }

    @Transactional
    public Marca criar(Marca marca) {
        // Regra de negócio: não permite marca duplicada
        if (marcaRepository.existsByNomeIgnoreCase(marca.getNome())) {
            throw new IllegalArgumentException(
                    "Já existe uma marca com o nome: " + marca.getNome());
        }
        return marcaRepository.save(marca);
    }

    @Transactional
    public Marca atualizar(Long id, Marca dadosAtualizados) {
        Marca marcaExistente = buscarPorId(id);
        marcaExistente.setNome(dadosAtualizados.getNome());
        marcaExistente.setPaisOrigem(dadosAtualizados.getPaisOrigem());
        return marcaRepository.save(marcaExistente);
    }

    @Transactional
    public void deletar(Long id) {
        Marca marca = buscarPorId(id);
        // Regra: não permite deletar marca que possui modelos cadastrados
        if (!marca.getModelos().isEmpty()) {
            throw new IllegalArgumentException(
                    "Não é possível excluir a marca pois ela possui modelos vinculados.");
        }
        marcaRepository.deleteById(id);
    }
}
