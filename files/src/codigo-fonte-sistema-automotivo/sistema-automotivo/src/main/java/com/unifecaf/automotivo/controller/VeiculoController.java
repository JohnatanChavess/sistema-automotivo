package com.unifecaf.automotivo.controller;

import com.unifecaf.automotivo.model.StatusVeiculo;
import com.unifecaf.automotivo.model.Veiculo;
import com.unifecaf.automotivo.service.VeiculoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * Controller REST principal — recurso VEICULO.
 *
 * Endpoints disponíveis:
 *  GET    /api/veiculos                    → listar todos
 *  GET    /api/veiculos/{id}               → buscar por ID
 *  GET    /api/veiculos/filtrar            → filtro combinado (RF05-RF08)
 *  POST   /api/veiculos/modelo/{modeloId}  → cadastrar (RF01)
 *  PUT    /api/veiculos/{id}               → atualizar (RF09)
 *  DELETE /api/veiculos/{id}               → remover   (RF10)
 */
@RestController
@RequestMapping("/api/veiculos")
@RequiredArgsConstructor
@Tag(name = "Veículos", description = "Operações CRUD e filtros do estoque de veículos")
public class VeiculoController {

    private final VeiculoService veiculoService;

    // ─────────────────────────────────────────────────────────────────
    // GET /api/veiculos  →  RF04: Consulta completa do estoque
    // ─────────────────────────────────────────────────────────────────
    @GetMapping
    @Operation(summary = "Lista todos os veículos do estoque")
    public ResponseEntity<List<Veiculo>> listarTodos() {
        return ResponseEntity.ok(veiculoService.listarTodos());
    }

    // ─────────────────────────────────────────────────────────────────
    // GET /api/veiculos/{id}  →  Consulta individual
    // ─────────────────────────────────────────────────────────────────
    @GetMapping("/{id}")
    @Operation(summary = "Busca um veículo específico pelo ID")
    public ResponseEntity<Veiculo> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(veiculoService.buscarPorId(id));
    }

    // ─────────────────────────────────────────────────────────────────
    // GET /api/veiculos/filtrar  →  RF05, RF06, RF07, RF08
    //
    // Exemplo de chamada:
    // GET /api/veiculos/filtrar?marca=Toyota&status=DISPONIVEL&precoMax=150000
    // GET /api/veiculos/filtrar?ano=2022&precoMin=80000&precoMax=200000
    // GET /api/veiculos/filtrar?modelo=corolla
    // ─────────────────────────────────────────────────────────────────
    @GetMapping("/filtrar")
    @Operation(summary = "Filtra veículos por múltiplos critérios simultaneamente",
               description = "Todos os parâmetros são opcionais e podem ser combinados livremente.")
    public ResponseEntity<List<Veiculo>> filtrar(
            @Parameter(description = "Nome (parcial) da marca. Ex: 'Toyota'")
            @RequestParam(required = false) String marca,

            @Parameter(description = "Nome (parcial) do modelo. Ex: 'Corolla'")
            @RequestParam(required = false) String modelo,

            @Parameter(description = "Ano de fabricação exato. Ex: 2022")
            @RequestParam(required = false) Integer ano,

            @Parameter(description = "Status do veículo: DISPONIVEL, VENDIDO ou RESERVADO")
            @RequestParam(required = false) StatusVeiculo status,

            @Parameter(description = "Preço mínimo da faixa de busca")
            @RequestParam(required = false) BigDecimal precoMin,

            @Parameter(description = "Preço máximo da faixa de busca")
            @RequestParam(required = false) BigDecimal precoMax) {

        List<Veiculo> resultado = veiculoService.buscarComFiltros(
                marca, modelo, ano, status, precoMin, precoMax);
        return ResponseEntity.ok(resultado);
    }

    // ─────────────────────────────────────────────────────────────────
    // POST /api/veiculos/modelo/{modeloId}  →  RF01: Cadastro
    // ─────────────────────────────────────────────────────────────────
    @PostMapping("/modelo/{modeloId}")
    @Operation(summary = "Cadastra um novo veículo no estoque",
               description = "O modeloId na URL define a qual modelo este veículo pertence.")
    public ResponseEntity<Veiculo> criar(
            @PathVariable Long modeloId,
            @Valid @RequestBody Veiculo veiculo) {
        Veiculo salvo = veiculoService.criar(modeloId, veiculo);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo); // HTTP 201
    }

    // ─────────────────────────────────────────────────────────────────
    // PUT /api/veiculos/{id}  →  RF09: Atualização
    // Aceita atualização parcial: apenas os campos enviados são alterados
    // ─────────────────────────────────────────────────────────────────
    @PutMapping("/{id}")
    @Operation(summary = "Atualiza preço, quilometragem, status ou observações de um veículo",
               description = "Somente os campos enviados no corpo serão atualizados.")
    public ResponseEntity<Veiculo> atualizar(
            @PathVariable Long id,
            @RequestBody Veiculo dadosAtualizados) {
        return ResponseEntity.ok(veiculoService.atualizar(id, dadosAtualizados));
    }

    // ─────────────────────────────────────────────────────────────────
    // DELETE /api/veiculos/{id}  →  RF10: Remoção
    // ─────────────────────────────────────────────────────────────────
    @DeleteMapping("/{id}")
    @Operation(summary = "Remove um veículo definitivamente do estoque")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        veiculoService.deletar(id);
        return ResponseEntity.noContent().build(); // HTTP 204
    }
}
