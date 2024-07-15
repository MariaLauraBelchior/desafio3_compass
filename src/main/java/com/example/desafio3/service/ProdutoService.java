package com.example.desafio3.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.desafio3.entity.Produto;
import com.example.desafio3.repository.ProdutoRepository;
import com.example.desafio3.service.exception.ProdutoInvalidoException;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public Produto criarProduto(Produto produto) {
        if (produto.getPreco() < 0) {
            throw new ProdutoInvalidoException("Preço do produto não pode ser negativo.");
        }
        return produtoRepository.save(produto);
    }

    public List<Produto> listaProduto() {
        return produtoRepository.findAll();
    }

    public Produto buscarProdutoById(Long id) {
        Optional<Produto> product = produtoRepository.findById(id);
        if (product.isEmpty()) {
            throw new ProdutoInvalidoException("Produto não encontrado.");
        }
        return product.get();
    }

    public Produto atualizarProduto(Long id, Produto produtoAtualizado ) { 
        Produto produto = buscarProdutoById(id);
        if (produtoAtualizado.getPreco() < 0) {
            throw new ProdutoInvalidoException("Preço do produto não pode ser negativo.");
        }
        produto.setNome(produtoAtualizado.getNome());
        produto.setCategoria(produtoAtualizado.getCategoria());
        produto.setPreco(produtoAtualizado.getPreco());
        produto.setEstoque(produtoAtualizado.getEstoque());
        produto.setAtivo(produtoAtualizado.getAtivo());

        return produtoRepository.save(produto);
    }

    public void deletarProduto(Long id) {
        Produto produto = buscarProdutoById(id);
        produto.setAtivo(false);
        produtoRepository.save(produto);
    }
}
