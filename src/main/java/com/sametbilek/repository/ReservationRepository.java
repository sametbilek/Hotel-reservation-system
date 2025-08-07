package com.sametbilek.repository;

import com.sametbilek.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("SELECT r FROM Reservation r WHERE r.roomNumber = :roomNumber AND r.checkInDate < :checkout AND r.checkOutDate > :checkin")
    List<Reservation> findConflictingReservations(String roomNumber, LocalDate checkin, LocalDate checkout);
}