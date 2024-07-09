package com.example.desafio3.repository;


import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.desafio3.entity.Venda;

public interface VendaRepository extends JpaRepository<Venda, Long> {
    List<Venda> findByDataVenda(LocalDateTime start, LocalDateTime end);
}
