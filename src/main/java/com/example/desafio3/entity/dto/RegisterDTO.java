package com.example.desafio3.entity.dto;

import com.example.desafio3.entity.enums.UsuarioRole;

public record RegisterDTO(String email, String senha, UsuarioRole role) {
    
}
