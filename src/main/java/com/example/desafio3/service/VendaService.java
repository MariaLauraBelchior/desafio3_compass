package com.example.desafio3.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.desafio3.entity.Produto;
import com.example.desafio3.entity.Venda;
import com.example.desafio3.repository.ProdutoRepository;
import com.example.desafio3.repository.VendaRepository;
import com.example.desafio3.service.exception.VendaInvalidaException;

@Service
public class VendaService {
    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    
    public VendaService(VendaRepository vendaRepository) {
        this.vendaRepository = vendaRepository;
    }

    public Venda criarVenda(Venda venda) {
        if (venda.getItens().isEmpty()) {
            throw new VendaInvalidaException("A venda deve ter pelo menos um produto.");
        }
        venda.getItens().forEach(item -> {
            Produto produto = produtoRepository.findById(item.getProduto().getId())
                    .orElseThrow(() -> new VendaInvalidaException("Produto não encontrado."));
            if (produto.getEstoque() < item.getQuantidade()) {
                throw new VendaInvalidaException("Estoque insuficiente para o produto: " + produto.getNome());
            }
            produto.setEstoque(produto.getEstoque() - item.getQuantidade());
            produtoRepository.save(produto);
        });
        return vendaRepository.save(venda);
    }

    public List<Venda> listaVenda() {
        return vendaRepository.findAll();
    }

    public List<Venda> ListaVendaPorData(LocalDateTime inicio, LocalDateTime fim) {
        return vendaRepository.findByDataVenda(inicio, fim);
    }

}
