package com.sametbilek.controller;

import com.sametbilek.model.Room;
import com.sametbilek.repository.RoomRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
@PreAuthorize("isAuthenticated()") // Tüm oda işlemleri için login olmayı zorunlu hale getirelim.
public class RoomController {

    private final RoomRepository roomRepository;

    public RoomController(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @PostMapping
    public ResponseEntity<Room> createRoom(@RequestBody Room room) {
        return ResponseEntity.status(201).body(roomRepository.save(room));
    }

    @GetMapping
    public ResponseEntity<List<Room>> getAllRooms(Authentication authentication) {
        // Gelen authentication nesnesinin null olup olmadığını kontrol etmeye artık gerek yok,
        // çünkü @PreAuthorize null olmasına zaten izin vermeyecektir.
        System.out.println("=================================================");
        System.out.println(">>> RoomController: /api/rooms endpoint'ine ulaşıldı.");
        System.out.println(">>> Gelen Principal (Kullanıcı): " + authentication.getName());
        System.out.println(">>> Gelen Yetkiler: " + authentication.getAuthorities());
        System.out.println("=================================================");

        return ResponseEntity.ok(roomRepository.findAll());
    }
}