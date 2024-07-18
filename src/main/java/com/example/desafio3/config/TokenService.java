package com.example.desafio3.config;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.desafio3.entity.PasswordResetToken;
import com.example.desafio3.entity.Usuario;
import com.example.desafio3.repository.PasswordResetTokenRepository;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    private final PasswordResetTokenRepository passwordResetTokenRepository;

    public TokenService(PasswordResetTokenRepository passwordResetTokenRepository) {
        this.passwordResetTokenRepository = passwordResetTokenRepository;
    }


    public String generateToken(Usuario usuario) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                .withIssuer("auth-api")
                .withSubject(usuario.getEmail())
                .withExpiresAt(genExpirationDate())
                .sign(algorithm);
            return token;
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao gerar token", exception);
        }
    }

    public String validateToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("auth-api")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception){
            return "";
        }
    }

    private Instant genExpirationDate(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

    public void createPasswordResetToken(Usuario usuario) {
        PasswordResetToken token = new PasswordResetToken(usuario);
        passwordResetTokenRepository.save(token);
    }

    public PasswordResetToken validatePasswordResetToken(String token) {
        return passwordResetTokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Token inválido ou expirado"));
    }
    
    public void invalidatePasswordResetToken(PasswordResetToken token) {
        passwordResetTokenRepository.delete(token);
    }
}

