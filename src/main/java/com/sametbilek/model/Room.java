package com.sametbilek.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "rooms")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String roomNumber; // "101", "Suit A" vb.

    @Column(nullable = false)
    private String type; // "Tek Kişilik", "Çift Kişilik"

    @Column(nullable = false)
    private BigDecimal pricePerNight; // Gecelik Fiyat
}