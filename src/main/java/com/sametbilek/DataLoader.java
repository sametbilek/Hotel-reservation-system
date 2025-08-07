package com.sametbilek;

import com.sametbilek.model.Room;
import com.sametbilek.model.User;
import com.sametbilek.repository.RoomRepository;
import com.sametbilek.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.util.Collections;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserService userService;
    private final RoomRepository roomRepository;

    public DataLoader(UserService userService, RoomRepository roomRepository) {
        this.userService = userService;
        this.roomRepository = roomRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Sadece bir test kullanıcısı oluştur
        if (userService.getAllUsers().isEmpty()) {
            System.out.println("Test kullanıcısı oluşturuluyor...");
            User user = new User();
            user.setUsername("user");
            user.setPassword("password123");
            // Rolü hard-coded olarak ROLE_USER veriyoruz
            userService.createUser(user);
            System.out.println("Test kullanıcısı oluşturuldu.");
        }

        // Odaları oluştur
        if (roomRepository.count() == 0) {
            System.out.println("Test odaları oluşturuluyor...");
            roomRepository.save(new Room(null, "101", "Tek Kişilik", new BigDecimal("1500.00")));
            roomRepository.save(new Room(null, "201", "Çift Kişilik", new BigDecimal("2500.00")));
            System.out.println("Test odaları oluşturuldu.");
        }
    }
}