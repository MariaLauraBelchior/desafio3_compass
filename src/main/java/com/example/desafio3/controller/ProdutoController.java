package com.example.desafio3.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.desafio3.entity.Produto;
import com.example.desafio3.service.ProdutoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {
    @Autowired
    private ProdutoService produtoService;


    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Produto> criarProduto(@Valid @RequestBody Produto produto) {
        return ResponseEntity.ok(produtoService.criarProduto(produto));
    }

    @GetMapping
    public ResponseEntity<List<Produto>> listaProduto() {
        return ResponseEntity.ok(produtoService.listaProduto());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscarProdutoById(@PathVariable Long id) {
        return ResponseEntity.ok(produtoService.buscarProdutoById(id));
    }


    @PutMapping("/{id}") 
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Produto> atualizarProduto(@PathVariable Long id, @RequestBody Produto produto) {
        return ResponseEntity.ok(produtoService.atualizarProduto(id, produto));
    }

    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletarProduto(@PathVariable Long id) {
        produtoService.deletarProduto(id);
        return ResponseEntity.noContent().build();
    }
}
