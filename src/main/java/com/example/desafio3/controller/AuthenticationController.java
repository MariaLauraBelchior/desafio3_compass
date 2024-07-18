package com.example.desafio3.controller;

import com.example.desafio3.config.TokenService;
import com.example.desafio3.entity.Usuario;
import com.example.desafio3.entity.dto.AuthenticationDTO;
import com.example.desafio3.entity.dto.LoginResponseDTO;
import com.example.desafio3.entity.dto.RegisterDTO;
import com.example.desafio3.repository.UsuarioRepository;
import com.example.desafio3.service.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import jakarta.validation.Valid;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioService usuarioService;


    
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody AuthenticationDTO data) {
        var userNamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.senha());
        var auth = this.authenticationManager.authenticate(userNamePassword);

        var token = tokenService.generateToken((Usuario) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    
    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody RegisterDTO data){
        if(this.usuarioRepository.findByEmail(data.email()) != null) 
        
        return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.senha());
        Usuario newUser = new Usuario(data.email(), encryptedPassword, data.role());

        this.usuarioRepository.save(newUser);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam("email") String email) {
        usuarioService.initiatePasswordReset(email);
        return ResponseEntity.ok("E-mail de redefinição de senha enviado.");
    }

    @PostMapping("/confirm-reset")
    public ResponseEntity<String> confirmReset(@RequestParam("token") String token, @RequestParam("senha") String password) {
        usuarioService.resetPassword(token, password);
        return ResponseEntity.ok("Senha redefinida com sucesso.");
    }
}
 