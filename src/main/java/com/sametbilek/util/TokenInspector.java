package com.sametbilek.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.List;

public class TokenInspector {

    public static void main(String[] args) {
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJhaG1ldCIsInJvbGVzIjpbIlJPTEVfVVNFUiJdLCJpYXQiOjE3NTQzOTA4ODIsImV4cCI6MTc1NDM5NDQ4Mn0.0Ir67i1A4-zS6rj3mH60BKxcpO6LvkcTHeQmqtj1tkE"; // Örnek token, siz kendinizinkini kullanın

        String secretKey = "5f4f89d361c470e8a7199c0d3a778b77051d9f8c6b24d775a6c3f0a5f8e9a2b5"; // DİKKAT: Kendi anahtarınızı kullanın!

        System.out.println("--- Token Çözümleniyor ---");

        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey.getBytes());

            DecodedJWT decodedJWT = JWT.require(algorithm).build().verify(token);

            String username = decodedJWT.getSubject();
            List<String> roles = decodedJWT.getClaim("roles").asList(String.class);
            Date issuedAt = decodedJWT.getIssuedAt();
            Date expiresAt = decodedJWT.getExpiresAt();

            System.out.println("Kullanıcı Adı (Username/Subject): " + username);
            System.out.println("Roller (Roles): " + roles);
            System.out.println("Oluşturulma Zamanı (Issued At): " + issuedAt);
            System.out.println("Geçerlilik Sonu (Expires At): " + expiresAt);


        } catch (Exception e) {
            System.err.println("Token doğrulama hatası! Token geçersiz veya süresi dolmuş olabilir.");
            e.printStackTrace();
        }
    }
}