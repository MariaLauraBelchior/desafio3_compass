package com.example.desafio3.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Products")
public class Product {
    
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private String category;
    private Double price;
    private Boolean estoque;  // produto não pode ser deletado 
    
    public Product(Integer id, String name, String category, Double price, Boolean estoque) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.estoque = estoque;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean getEstoque() {
        return estoque;
    }

    public void setEstoque(Boolean estoque) {
        this.estoque = estoque;
    }

    
    
}
