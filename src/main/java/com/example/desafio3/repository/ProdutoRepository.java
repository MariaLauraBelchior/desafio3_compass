package com.example.desafio3.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.desafio3.entity.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

}
