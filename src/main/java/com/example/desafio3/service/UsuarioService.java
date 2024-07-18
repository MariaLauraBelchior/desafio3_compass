package com.example.desafio3.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.desafio3.config.TokenService;
import com.example.desafio3.entity.PasswordResetToken;
import com.example.desafio3.entity.Usuario;
import com.example.desafio3.repository.UsuarioRepository;

@Service
public class UsuarioService implements UserDetailsService{

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TokenService tokenService;

    /*@Autowired
    private EmailService emailService;*/

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByEmail(username);
    }

    public void initiatePasswordReset(String email) {
        /*Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        PasswordResetToken resetToken = tokenService.createPasswordResetToken(email); 
        String resetLink = "http://localhost:8080/api/auth/confirm-reset?token=" + resetToken.getToken();
        emailService.sendEmail(usuario.getEmail(), "Solicitação de Redefinição de Senha", 
                "Para redefinir sua senha, clique no link abaixo:\n" + resetLink);*/
    }

    public void resetPassword(String token, String newPassword) {
        PasswordResetToken resetToken = tokenService.validatePasswordResetToken(token);

        if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token expirado");
        }

        Usuario usuario = resetToken.getUsuario();
        usuario.setSenha(passwordEncoder.encode(newPassword));
        usuarioRepository.save(usuario);

        tokenService.invalidatePasswordResetToken(resetToken);
    }
}
