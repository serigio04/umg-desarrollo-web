package com.proyecto.auth.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
public class JwtTokenProvider {

    @Value("classpath:keys/private_key.pem")
    private Resource privateKeyResource;

    @Value("${jwt.expiration-ms:86400000}") // 24 horas por defecto
    private long jwtExpirationMs;

    private PrivateKey getPrivateKey() throws Exception {
        String key;
        // Lectura segura mediante InputStream compatible con JAR de Docker
        try (InputStream inputStream = privateKeyResource.getInputStream()) {
            key = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        }

        key = key.replace("-----BEGIN PRIVATE KEY-----", "")
                 .replace("-----END PRIVATE KEY-----", "")
                 .replace("-----BEGIN RSA PRIVATE KEY-----", "")
                 .replace("-----END RSA PRIVATE KEY-----", "")
                 .replaceAll("\\s+", "");

        byte[] keyBytes = Base64.getDecoder().decode(key);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(spec);
    }

    public String generarToken(String username, List<String> roles) throws Exception {
        Date ahora = new Date();
        Date expiracion = new Date(ahora.getTime() + jwtExpirationMs);

        return Jwts.builder()
                .setSubject(username)
                .claim("roles", roles)
                .setIssuedAt(ahora)
                .setExpiration(expiracion)
                .signWith(getPrivateKey(), SignatureAlgorithm.RS256)
                .compact();
    }
}