package com.example.desafio3.entity.enums;

public enum UsuarioRole {
    ADMIN("admin"),
    USUARIO("usuario");

    private String role;

    UsuarioRole(String role) {
        this.role = role;

    }

    public String getRole() {
        return role;
    }
}
