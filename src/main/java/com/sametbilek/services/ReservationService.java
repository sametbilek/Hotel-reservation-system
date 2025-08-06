package com.sametbilek.services;

import com.sametbilek.model.Reservation;
import com.sametbilek.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    /**
     * Yeni bir rezervasyon oluşturur ve veritabanına kaydeder.
     * @param reservation Kaydedilecek rezervasyon bilgileri.
     * @return Kaydedilen rezervasyon nesnesi.
     */
    public Reservation createReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    /**
     * Veritabanındaki tüm rezervasyonları listeler.
     * @return Rezervasyon listesi.
     */
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }
}