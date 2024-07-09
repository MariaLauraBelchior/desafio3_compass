package com.example.desafio3.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "Products")
public class Produto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nome;

    @NotBlank
    private String categoria;

    @NotNull
    @Min(value = 0, message = "O preço deve ser positivo")
    private Double preco;

    @NotNull
    @Min(value = 0, message = "A quantidade em estoque deve ser positiva")
    private Integer estoque;

    private Boolean ativo = true;  
    
}
