package com.sametbilek.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("/public")
    public String publicEndpoint() {
        return "Burası herkese açık bir alan!";
    }

    @GetMapping("/secure/all")
    @PreAuthorize("isAuthenticated()")
    public String secureEndpoint(Principal principal) {
        return "Merhaba, " + principal.getName() + "! Bu güvenli alanı görebiliyorsun.";
    }

    @GetMapping("/secure/user")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String userEndpoint(Principal principal) {
        return "Merhaba USER: " + principal.getName() + "! Burası sadece kullanıcılara özel.";
    }


    @GetMapping("/secure/admin")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String adminEndpoint(Principal principal) {
        return "Merhaba ADMIN: " + principal.getName() + "! Burası sadece adminlere özel.";
    }
}