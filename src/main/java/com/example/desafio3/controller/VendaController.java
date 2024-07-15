package com.example.desafio3.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.desafio3.entity.Venda;
import com.example.desafio3.service.VendaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/vendas")
public class VendaController {
    @Autowired
    private VendaService vendaService;

    @Operation
    @ApiResponse
    @PostMapping
    public ResponseEntity<Venda> criarVenda(@Valid @RequestBody Venda venda ) {
        return ResponseEntity.ok(vendaService.criarVenda(venda));
    }

    @GetMapping
    public ResponseEntity<List<Venda>> listaVenda() {
        return ResponseEntity.ok(vendaService.listaVenda());
    }

    @GetMapping("/filtro")
    public ResponseEntity<List<Venda>> listaVendaPorData(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim) {
        return ResponseEntity.ok(vendaService.listaVendaPorData(inicio, fim));
    }

    @GetMapping("/relatorio/semanal")
    public ResponseEntity<List<Venda>> relatorioSemanal(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicioDaSemana) {
        return ResponseEntity.ok(vendaService.relatorioSemanal(inicioDaSemana));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Venda> atualizarVenda(@PathVariable Long id, @Valid @RequestBody Venda vendaAtualizada) {
        return ResponseEntity.ok(vendaService.atualizarVenda(id, vendaAtualizada));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarVenda(@PathVariable Long id) {
        vendaService.deletarVenda(id);
        return ResponseEntity.noContent().build();
    }
}
