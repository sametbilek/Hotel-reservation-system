package com.sametbilek.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
// DEĞİŞİKLİK 1: UserDetails arayüzünü implemente et
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private List<String> roles;


    // --- DEĞİŞİKLİK 2: UserDetails'in gerektirdiği metotları ekle ---

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Bu metot, String listesi olan rollerimizi Spring Security'nin
        // anladığı GrantedAuthority listesine çevirir.
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    // Bu metotlar UserDetails arayüzü için zorunludur.
    // Lombok'un @Data anotasyonu getUsername() ve getPassword() metotlarını
    // bizim için zaten oluşturur, bu yüzden onları tekrar yazmamıza gerek yok.

    @Override
    public boolean isAccountNonExpired() {
        return true; // Hesap süresinin dolmadığını belirtir
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Hesabın kilitli olmadığını belirtir
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Parolanın süresinin dolmadığını belirtir
    }

    @Override
    public boolean isEnabled() {
        return true; // Hesabın aktif olduğunu belirtir
    }
}