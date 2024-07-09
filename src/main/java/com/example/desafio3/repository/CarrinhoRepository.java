package com.example.desafio3.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.desafio3.entity.Carrinho;

public interface CarrinhoRepository extends JpaRepository<Carrinho, Long>{
    
}
