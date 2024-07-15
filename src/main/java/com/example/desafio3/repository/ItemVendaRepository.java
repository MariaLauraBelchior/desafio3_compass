package com.example.desafio3.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.desafio3.entity.ItemVenda;
import java.util.List;


public interface ItemVendaRepository extends JpaRepository<ItemVenda, Long>{
    List<ItemVenda> findByVendaId(Long vendaId);
}
