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
// Bu anotasyon sayesinde, bu controller'daki tüm metotlara erişmek için
// geçerli bir token (login olmuş bir çalışan) zorunludur.
@PreAuthorize("isAuthenticated()")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    /**
     * Yeni bir müşteri rezervasyonu oluşturur.
     * @param reservation Müşteri ve rezervasyon bilgilerini içeren JSON nesnesi.
     * @param principal O an login olmuş ve işlemi yapan çalışanın kimlik bilgileri.
     * @return Oluşturulan rezervasyon veya hata mesajı.
     */
    @PostMapping
    public ResponseEntity<?> createReservation(@RequestBody Reservation reservation, Principal principal) {
        try {
            // Token'dan login olmuş çalışanın username'ini al
            String employeeUsername = principal.getName();

            // Servis metodunu çağırarak rezervasyonu oluştur
            Reservation createdReservation = reservationService.createReservation(reservation, employeeUsername);

            // Başarılı olursa 201 Created durum koduyla rezervasyonu döndür
            return new ResponseEntity<>(createdReservation, HttpStatus.CREATED);
        } catch (Exception e) {
            // Servisten gelen "Oda dolu", "Oda bulunamadı" gibi hataları yakala
            // ve 409 Conflict durum koduyla hatanın sebebini kullanıcıya göster.
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    /**
     * Sistemdeki tüm rezervasyonları listeler.
     * @return Rezervasyon listesi.
     */
    @GetMapping
    public ResponseEntity<List<Reservation>> getAllReservations() {
        List<Reservation> reservations = reservationService.getAllReservations();
        return ResponseEntity.ok(reservations);
    }

    /**
     * Belirtilen ID'ye sahip rezervasyonu siler.
     * @param id Silinecek rezervasyonun ID'si.
     * @return Başarı durumunda 204 No Content.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        try {
            reservationService.deleteReservation(id);
            // Silme işlemi başarılı olduğunda içeriği olmayan bir "başarılı" yanıtı döner.
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            // Eğer silinecek rezervasyon bulunamazsa 404 Not Found döner.
            return ResponseEntity.notFound().build();
        }
    }
}