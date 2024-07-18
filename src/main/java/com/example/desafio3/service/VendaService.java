package com.example.desafio3.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.example.desafio3.entity.Produto;
import com.example.desafio3.entity.Usuario;
import com.example.desafio3.entity.Venda;
import com.example.desafio3.repository.ProdutoRepository;
import com.example.desafio3.repository.UsuarioRepository;
import com.example.desafio3.repository.VendaRepository;
import com.example.desafio3.service.exception.ResourceNotFoundException;
import com.example.desafio3.service.exception.VendaInvalidaException;

@Service
public class VendaService {
    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    
    public VendaService(VendaRepository vendaRepository) {
        this.vendaRepository = vendaRepository;
    }

    @CacheEvict(value = "vendas", allEntries = true)
    public Venda criarVenda(Venda venda, Usuario usuario) {
        if (venda.getItens().isEmpty()) {
            throw new VendaInvalidaException("A venda deve ter pelo menos um produto.");
        }

        Usuario usuarioEncontrado  = usuarioRepository.findById(usuario.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario não encontrado"));
        venda.setUsuario(usuarioEncontrado );

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

    public Venda atualizarVenda(Long id, Venda vendaAtualizada) {
        Venda vendaExistente = vendaRepository.findById(id)
                .orElseThrow(() -> new VendaInvalidaException("Venda não encontrada."));
            
            vendaAtualizada.setId(vendaExistente.getId());
    
            
            vendaExistente.getItens().clear();
            vendaExistente.getItens().addAll(vendaAtualizada.getItens());
                
            
            vendaExistente.setDataVenda(vendaAtualizada.getDataVenda());
            vendaExistente.setStatus(vendaAtualizada.getStatus());
            vendaExistente.setPagamento(vendaAtualizada.getPagamento());
                
            return criarVenda(vendaExistente, vendaExistente.getUsuario());
    }

    @Cacheable(value = "vendas")
    public List<Venda> listaVenda() {
        return vendaRepository.findAll();
    }

    @Cacheable(value = "vendas", key = "#inicio.toString() + '-' + #fim.toString()")
    public List<Venda> listaVendaPorData(LocalDateTime inicio, LocalDateTime fim) {
        return vendaRepository.findByDataVendaBetween(inicio, fim);
    }

    public List<Venda> relatorioMensal(int ano, int mes) {
        LocalDateTime inicio = LocalDateTime.of(ano, mes, 1, 0, 0);
        LocalDateTime fim = inicio.plusMonths(1).minusSeconds(1);
        return listaVendaPorData(inicio, fim);
    }

    public List<Venda> relatorioSemanal(LocalDateTime inicioDaSemana) {
        LocalDateTime fimDaSemana = inicioDaSemana.plusDays(7).minusSeconds(1);
        return listaVendaPorData(inicioDaSemana, fimDaSemana);
    }

    public void deletarVenda(Long id) {
        Venda venda = vendaRepository.findById(id)
                .orElseThrow(() -> new VendaInvalidaException("Venda não encontrada."));
        vendaRepository.delete(venda);
    }

}
