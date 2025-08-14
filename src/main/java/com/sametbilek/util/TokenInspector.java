// Lütfen bu dosyayı backend projenizin dışında ayrı bir yerde test için kullanın
// veya projenizin test paketleri altına ekleyin.
// Gerekli kütüphaneleri (io.jsonwebtoken) projenize eklemeyi unutmayın.
package com.sametbilek.util;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class TokenInspector {

    // BU ANAHTARIN JwtService'İNİZDEKİ İLE AYNI OLDUĞUNDAN EMİN OLUN!
    private static final String SECRET_KEY = "5f4f89d361c470e8a7199c0d3a778b77051d9f8c6b24d775a6c3f0a5f8e9a2b5";

    private static Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public static void main(String[] args) {
        // BURAYA FRONTEND'DEN ALDIĞINIZ YENİ BİR TOKEN'I YAPIŞTIRIN
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJyZWNlcHRpb25pc3QiLCJpYXQiOjE3MjMzNTQyMTMsImV4cCI6MTcyMzQ0MDYxM30.Y4aJ8o-j-g_...";

        System.out.println("--- Token Çözümleniyor (JJWT Kütüphanesi ile) ---");

        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String username = claims.getSubject();
            Date issuedAt = claims.getIssuedAt();
            Date expiresAt = claims.getExpiration();

            // Not: Bizim oluşturduğumuz token'da roller payload içinde değil.
            // Roller, kullanıcı veritabanından çekildiğinde UserDetails nesnesinden alınır.
            // Bu daha güvenli bir yöntemdir.

            System.out.println("Kullanıcı Adı (Subject): " + username);
            System.out.println("Oluşturulma Zamanı (Issued At): " + issuedAt);
            System.out.println("Geçerlilik Sonu (Expires At): " + expiresAt);

        } catch (Exception e) {
            System.err.println("Token doğrulama hatası! Muhtemel sebepler:");
            System.err.println("1. Token'ın süresi dolmuş olabilir.");
            System.err.println("2. SECRET_KEY yanlış olabilir.");
            System.err.println("3. Token'ın yapısı bozulmuş olabilir.");
            e.printStackTrace();
        }
    }
}