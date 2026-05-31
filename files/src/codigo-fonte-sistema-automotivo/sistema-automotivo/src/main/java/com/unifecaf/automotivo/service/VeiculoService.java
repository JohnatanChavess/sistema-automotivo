package com.unifecaf.automotivo.service;

import com.unifecaf.automotivo.exception.RecursoNaoEncontradoException;
import com.unifecaf.automotivo.model.Modelo;
import com.unifecaf.automotivo.model.StatusVeiculo;
import com.unifecaf.automotivo.model.Veiculo;
import com.unifecaf.automotivo.repository.VeiculoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Year;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VeiculoService {

    private final VeiculoRepository veiculoRepository;
    private final ModeloService modeloService;

    // ── Leitura ────────────────────────────────────────────────────────

    public List<Veiculo> listarTodos() {
        return veiculoRepository.findAll();
    }

    public Veiculo buscarPorId(Long id) {
        return veiculoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Veículo", id));
    }

    public List<Veiculo> buscarComFiltros(
            String nomeMarca,
            String nomeModelo,
            Integer ano,
            StatusVeiculo status,
            BigDecimal precoMin,
            BigDecimal precoMax) {

        validarFaixaPreco(precoMin, precoMax);
        return veiculoRepository.findWithFilters(
                nomeMarca, nomeModelo, ano, status, precoMin, precoMax);
    }

    @Transactional
    public Veiculo criar(Long modeloId, Veiculo veiculo) {
        Modelo modelo = modeloService.buscarPorId(modeloId);

        validarAnoFabricacao(veiculo.getAnoFabricacao());

        veiculo.setModelo(modelo);
        veiculo.setDataCadastro(LocalDate.now()); // Preenchimento automático
        if (veiculo.getStatus() == null) {
            veiculo.setStatus(StatusVeiculo.DISPONIVEL);
        }

        return veiculoRepository.save(veiculo);
    }


    @Transactional
    public Veiculo atualizar(Long id, Veiculo dadosAtualizados) {
        Veiculo veiculoExistente = buscarPorId(id);

        if (dadosAtualizados.getPreco() != null) {
            if (dadosAtualizados.getPreco().compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("O preço deve ser maior que zero.");
            }
            veiculoExistente.setPreco(dadosAtualizados.getPreco());
        }
        if (dadosAtualizados.getQuilometragem() != null) {
            if (dadosAtualizados.getQuilometragem() < veiculoExistente.getQuilometragem()) {
                throw new IllegalArgumentException(
                        "A quilometragem não pode ser menor que a atual: "
                                + veiculoExistente.getQuilometragem());
            }
            veiculoExistente.setQuilometragem(dadosAtualizados.getQuilometragem());
        }
        if (dadosAtualizados.getStatus() != null) {
            veiculoExistente.setStatus(dadosAtualizados.getStatus());
        }
        if (dadosAtualizados.getCor() != null) {
            veiculoExistente.setCor(dadosAtualizados.getCor());
        }
        if (dadosAtualizados.getObservacoes() != null) {
            veiculoExistente.setObservacoes(dadosAtualizados.getObservacoes());
        }

        return veiculoRepository.save(veiculoExistente);
    }


    @Transactional
    public void deletar(Long id) {
        buscarPorId(id); 
        veiculoRepository.deleteById(id);
    }

    private void validarAnoFabricacao(Integer ano) {
        int anoAtual = Year.now().getValue();
        if (ano < 1990 || ano > anoAtual + 1) {
            throw new IllegalArgumentException(
                    String.format("Ano de fabricação inválido: %d. Deve estar entre 1990 e %d.",
                            ano, anoAtual + 1));
        }
    }

    private void validarFaixaPreco(BigDecimal precoMin, BigDecimal precoMax) {
        if (precoMin != null && precoMax != null
                && precoMin.compareTo(precoMax) > 0) {
            throw new IllegalArgumentException(
                    "O preço mínimo não pode ser maior que o preço máximo.");
        }
    }
}
