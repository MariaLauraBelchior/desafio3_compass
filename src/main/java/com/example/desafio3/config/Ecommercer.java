package com.example.desafio3.config;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


import com.example.desafio3.entity.Produto;
import com.example.desafio3.entity.Venda;
import com.example.desafio3.entity.enums.StatusVenda;
import com.example.desafio3.repository.ProdutoRepository;
import com.example.desafio3.repository.VendaRepository;

@Component
public class Ecommercer implements CommandLineRunner {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private VendaRepository vendaRepository;

    @Override
    public void run(String... args) throws Exception {
        Produto produto1 = new Produto();
        produto1.setNome("Tanquinho");
        produto1.setPreco(1000.00);
        produto1.setEstoque(12);
        produto1.setCategoria("Eletrodomestico");
        produto1.setAtivo(true);

        Produto produto2 = new Produto();
        produto2.setNome("Smart TV");
        produto2.setPreco(2500.0);
        produto2.setEstoque(50);
        produto2.setCategoria("Eletrodomestico");
        produto2.setAtivo(true);

        Venda venda1 = new Venda();
        venda1.setDataVenda(LocalDateTime.now());
        venda1.setStatus(StatusVenda.CONCLUIDA);
        venda1.setTotal(1000.0);
        venda1.setPago(true);


        produtoRepository.save(produto2);
        produtoRepository.save(produto1);

        vendaRepository.save(venda1);
    }
    
}
