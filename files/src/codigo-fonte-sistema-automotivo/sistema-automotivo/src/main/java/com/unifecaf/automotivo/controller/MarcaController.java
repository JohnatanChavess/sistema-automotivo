package com.unifecaf.automotivo.controller;

import com.unifecaf.automotivo.model.Marca;
import com.unifecaf.automotivo.service.MarcaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/marcas")
@RequiredArgsConstructor
@Tag(name = "Marcas", description = "Operações de gerenciamento de marcas de veículos")
public class MarcaController {

    private final MarcaService marcaService;

    @GetMapping
    @Operation(summary = "Lista todas as marcas cadastradas")
    public ResponseEntity<List<Marca>> listarTodas() {
        return ResponseEntity.ok(marcaService.listarTodas());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca uma marca pelo ID")
    public ResponseEntity<Marca> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(marcaService.buscarPorId(id));
    }

    @PostMapping
    @Operation(summary = "Cadastra uma nova marca")
    public ResponseEntity<Marca> criar(@Valid @RequestBody Marca marca) {
        Marca marcaSalva = marcaService.criar(marca);
        return ResponseEntity.status(HttpStatus.CREATED).body(marcaSalva);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza os dados de uma marca")
    public ResponseEntity<Marca> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody Marca marca) {
        return ResponseEntity.ok(marcaService.atualizar(id, marca));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove uma marca (somente se não tiver modelos vinculados)")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        marcaService.deletar(id);
        return ResponseEntity.noContent().build(); // HTTP 204
    }
}
