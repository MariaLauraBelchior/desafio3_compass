package com.example.desafio3.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.desafio3.entity.Produto;
import com.example.desafio3.repository.ProdutoRepository;

@Component
public class Ecommercer implements CommandLineRunner {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Override
    public void run(String... args) throws Exception {
        Produto produto1 = new Produto();
        produto1.setNome("Tanquinho");
        produto1.setPreco(1000.00);
        produto1.setEstoque(12);
        produto1.setCategoria("Eletrodomestico");
        produto1.setAtivo(true);


        produtoRepository.save(produto1);
    }
    
}
