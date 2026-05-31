package com.unifecaf.automotivo.controller;

import com.unifecaf.automotivo.model.Modelo;
import com.unifecaf.automotivo.service.ModeloService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/modelos")
@RequiredArgsConstructor
@Tag(name = "Modelos", description = "Operações de gerenciamento de modelos de veículos")
public class ModeloController {

    private final ModeloService modeloService;

    @GetMapping
    @Operation(summary = "Lista todos os modelos")
    public ResponseEntity<List<Modelo>> listarTodos() {
        return ResponseEntity.ok(modeloService.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um modelo pelo ID")
    public ResponseEntity<Modelo> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(modeloService.buscarPorId(id));
    }

    @GetMapping("/marca/{marcaId}")
    @Operation(summary = "Lista todos os modelos de uma marca específica")
    public ResponseEntity<List<Modelo>> listarPorMarca(@PathVariable Long marcaId) {
        return ResponseEntity.ok(modeloService.listarPorMarca(marcaId));
    }

    @PostMapping("/marca/{marcaId}")
    @Operation(summary = "Cadastra um novo modelo vinculado a uma marca")
    public ResponseEntity<Modelo> criar(
            @PathVariable Long marcaId,
            @Valid @RequestBody Modelo modelo) {
        Modelo modeloSalvo = modeloService.criar(marcaId, modelo);
        return ResponseEntity.status(HttpStatus.CREATED).body(modeloSalvo);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza os dados de um modelo")
    public ResponseEntity<Modelo> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody Modelo modelo) {
        return ResponseEntity.ok(modeloService.atualizar(id, modelo));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove um modelo (somente se não tiver veículos vinculados)")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        modeloService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
