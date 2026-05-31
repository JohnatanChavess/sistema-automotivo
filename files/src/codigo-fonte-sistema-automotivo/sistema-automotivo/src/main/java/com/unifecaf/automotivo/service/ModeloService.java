package com.unifecaf.automotivo.service;

import com.unifecaf.automotivo.exception.RecursoNaoEncontradoException;
import com.unifecaf.automotivo.model.Marca;
import com.unifecaf.automotivo.model.Modelo;
import com.unifecaf.automotivo.repository.ModeloRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ModeloService {

    private final ModeloRepository modeloRepository;
    private final MarcaService marcaService; 

    public List<Modelo> listarTodos() {
        return modeloRepository.findAll();
    }

    public List<Modelo> listarPorMarca(Long marcaId) {
        marcaService.buscarPorId(marcaId);
        return modeloRepository.findByMarcaId(marcaId);
    }

    public Modelo buscarPorId(Long id) {
        return modeloRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Modelo", id));
    }

    @Transactional
    public Modelo criar(Long marcaId, Modelo modelo) {
        Marca marca = marcaService.buscarPorId(marcaId);
        modelo.setMarca(marca);
        return modeloRepository.save(modelo);
    }

    @Transactional
    public Modelo atualizar(Long id, Modelo dadosAtualizados) {
        Modelo modeloExistente = buscarPorId(id);
        modeloExistente.setNome(dadosAtualizados.getNome());
        modeloExistente.setCategoria(dadosAtualizados.getCategoria());
        return modeloRepository.save(modeloExistente);
    }

    @Transactional
    public void deletar(Long id) {
        Modelo modelo = buscarPorId(id);
        if (!modelo.getVeiculos().isEmpty()) {
            throw new IllegalArgumentException(
                    "Não é possível excluir o modelo pois ele possui veículos vinculados.");
        }
        modeloRepository.deleteById(id);
    }
}
