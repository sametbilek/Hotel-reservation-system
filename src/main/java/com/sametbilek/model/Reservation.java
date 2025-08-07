package com.sametbilek.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "reservations")
@Data
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Müşteri Bilgileri
    @Column(nullable = false)
    private String customerFirstName;

    @Column(nullable = false)
    private String customerLastName;

    @Column(nullable = false)
    private String tckn;

    // Rezervasyon Detayları
    @Column(nullable = false)
    private LocalDate checkInDate;

    @Column(nullable = false)
    private LocalDate checkOutDate;

    @Column(name = "total_price", precision = 10, scale = 2) // <-- Önerilen ekleme
    private BigDecimal totalPrice;

    // --- İLİŞKİSİZ ALANLAR ---
    @Column(nullable = false)
    private String roomNumber; // Hangi odanın rezerve edildiğini numara olarak tutar

    @Column(nullable = false)
    private String createdByEmployeeUsername; // Rezervasyonu hangi çalışanın yaptığını username olarak tutar
}