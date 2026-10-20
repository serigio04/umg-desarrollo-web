package com.proyecto.backend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Component
public class JwtUtils {

    @Value("classpath:keys/public_key.pem")
    private Resource publicKeyResource;

    private PublicKey publicKey;

    // Se ejecuta UNA SOLA VEZ al iniciar la aplicación en Spring Boot
    @PostConstruct
    public void init() {
        try {
            this.publicKey = loadPublicKey();
        } catch (Exception e) {
            throw new RuntimeException("Error crítico: No se pudo cargar la clave pública RSA en JwtUtils", e);
        }
    }

    private PublicKey loadPublicKey() throws Exception {
        String key;
        try (InputStream inputStream = publicKeyResource.getInputStream()) {
            key = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        }

        key = key.replace("-----BEGIN PUBLIC KEY-----", "")
                 .replace("-----END PUBLIC KEY-----", "")
                 .replaceAll("\\s+", "");

        byte[] keyBytes = Base64.getDecoder().decode(key);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(spec);
    }

    public Claims validarToken(String token) throws Exception {
        // Reutiliza el objeto PublicKey previamente cargado en memoria RAM
        return Jwts.parserBuilder()
                .setSigningKey(this.publicKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}