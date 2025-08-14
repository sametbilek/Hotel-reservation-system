package com.sametbilek.controller;

import com.sametbilek.model.Reservation;
import com.sametbilek.services.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/reservations")
@PreAuthorize("isAuthenticated()") // Bu satır sayesinde tüm metotlar kimlik doğrulaması gerektirir.
public class ReservationController {

    private final ReservationService reservationService;

    // Artık diğer servislere ihtiyaç yok, constructor'dan kaldırıldı.
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<?> createReservation(@RequestBody Reservation reservation, Principal principal) {
        try {
            String employeeUsername = principal.getName();
            Reservation createdReservation = reservationService.createReservation(reservation, employeeUsername);
            return new ResponseEntity<>(createdReservation, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    /**
     * Sistemdeki tüm rezervasyonları listeler.
     * Spring Security, cookie'yi kontrol edip kullanıcıyı doğruladıktan sonra bu metoda erişim izni verir.
     * Manuel olarak login işlemi yapmaya GEREK YOKTUR.
     */
    @GetMapping
    public ResponseEntity<List<Reservation>> getAllReservations() {
        // İçerideki tüm manuel login mantığı kaldırıldı.
        // Spring Security, bu metoda erişimden önce kimliği zaten doğrulamış olur.
        List<Reservation> reservations = reservationService.getAllReservations();
        return ResponseEntity.ok(reservations);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        try {
            reservationService.deleteReservation(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}