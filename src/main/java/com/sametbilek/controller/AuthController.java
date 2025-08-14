package com.sametbilek.controller;

import com.sametbilek.services.AuthService;
import com.sametbilek.services.JwtService;
import com.sametbilek.services.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final AuthService authService;
    private final UserService userService;
    private final JwtService jwtService;

    public AuthController(AuthenticationManager authenticationManager, AuthService authService,
                          UserService userService, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.authService = authService;
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");

        // 1. Kullanıcı adı ve şifreyi doğrula. Başarısız olursa exception fırlatır.
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        // 2. Kullanıcı bilgilerini al ve JWT oluştur.
        final UserDetails userDetails = authService.loadUserByUsername(username);
        final String jwt = jwtService.generateToken(userDetails);

        // 3. Güvenli, HttpOnly bir cookie oluştur.
        ResponseCookie cookie = ResponseCookie.from("jwt-token", jwt)
                .httpOnly(true)       // JavaScript'in cookie'ye erişmesini engeller (EN GÜVENLİ YÖNTEM).
                .secure(true)         // Production ortamında (HTTPS) true olmalı. Geliştirme için false yapabilirsiniz.
                .path("/")            // Cookie'nin tüm site için geçerli olmasını sağlar.
                .maxAge(24 * 60 * 60) // Cookie'nin geçerlilik süresi (saniye cinsinden, burada 1 gün).
                .build();

        // 4. Cevap olarak sadece başarılı mesajı ve cookie'yi header'da gönder.
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(Map.of("message", "Giriş başarılı!"));
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody Map<String, String> request) {
        try {
            userService.registerUser(request.get("username"), request.get("password"));
            return ResponseEntity.ok("Kullanıcı başarıyla kaydedildi!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}