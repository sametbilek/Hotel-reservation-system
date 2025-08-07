package com.sametbilek.services;

import com.sametbilek.model.Reservation;
import com.sametbilek.model.Room;
import com.sametbilek.repository.ReservationRepository;
import com.sametbilek.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;

    public ReservationService(ReservationRepository reservationRepository, RoomRepository roomRepository) {
        this.reservationRepository = reservationRepository;
        this.roomRepository = roomRepository;
    }

    public Reservation createReservation(Reservation reservationData, String employeeUsername) {
        String roomNumber = reservationData.getRoomNumber();
        LocalDate checkIn = reservationData.getCheckInDate();
        LocalDate checkOut = reservationData.getCheckOutDate();

        // 1. Müsaitlik Kontrolü
        if (checkIn.isAfter(checkOut) || checkIn.isEqual(checkOut)) {
            throw new IllegalStateException("Çıkış tarihi, giriş tarihinden sonra olmalıdır.");
        }
        List<Reservation> conflicts = reservationRepository.findConflictingReservations(roomNumber, checkIn, checkOut);
        if (!conflicts.isEmpty()) {
            throw new IllegalStateException("Oda bu tarihler arasında dolu!");
        }

        // 2. Fiyat Hesaplama
        Room room = roomRepository.findByRoomNumber(roomNumber)
                .orElseThrow(() -> new RuntimeException("'" + roomNumber + "' numaralı oda bulunamadı!"));
        long numberOfNights = ChronoUnit.DAYS.between(checkIn, checkOut);
        reservationData.setTotalPrice(room.getPricePerNight().multiply(new java.math.BigDecimal(numberOfNights)));

        // 3. Çalışan Bilgisini Ekle ve Kaydet
        reservationData.setCreatedByEmployeeUsername(employeeUsername);
        return reservationRepository.save(reservationData);
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }
}