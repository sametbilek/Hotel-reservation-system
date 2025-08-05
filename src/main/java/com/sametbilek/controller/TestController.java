package com.sametbilek.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/test")
public class TestController {

    // Herkesin erişebileceği, token gerektirmeyen bir endpoint
    @GetMapping("/public")
    public String publicEndpoint() {
        return "Burası herkese açık bir alan!";
    }

    // Sadece kimliği doğrulanmış (login olmuş) herhangi bir kullanıcının erişebileceği bir endpoint
    @GetMapping("/secure/all")
    @PreAuthorize("isAuthenticated()")
    public String secureEndpoint(Principal principal) {
        return "Merhaba, " + principal.getName() + "! Bu güvenli alanı görebiliyorsun.";
    }

    // Sadece 'ROLE_USER' yetkisine sahip kullanıcıların erişebileceği bir endpoint
    @GetMapping("/secure/user")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String userEndpoint(Principal principal) {
        return "Merhaba USER: " + principal.getName() + "! Burası sadece kullanıcılara özel.";
    }

    // Sadece 'ROLE_ADMIN' yetkisine sahip kullanıcıların erişebileceği bir endpoint
    @GetMapping("/secure/admin")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String adminEndpoint(Principal principal) {
        return "Merhaba ADMIN: " + principal.getName() + "! Burası sadece adminlere özel.";
    }
}