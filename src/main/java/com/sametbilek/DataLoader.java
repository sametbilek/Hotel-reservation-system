package com.sametbilek; // Kendi paket yapınıza göre düzenleyin

import com.sametbilek.model.Room;
import com.sametbilek.model.User;
import com.sametbilek.repository.RoomRepository;
import com.sametbilek.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoomRepository roomRepository; // <-- EKLENDİ

    // CONSTRUCTOR GÜNCELLENDİ
    public DataLoader(UserRepository userRepository, PasswordEncoder passwordEncoder, RoomRepository roomRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roomRepository = roomRepository; // <-- EKLENDİ
    }

    @Override
    public void run(String... args) throws Exception {
        // Kullanıcıları oluştur
        if (userRepository.count() == 0) {
            System.out.println("Veritabanı boş, test kullanıcıları oluşturuluyor...");
            User user = new User();
            user.setUsername("Yazan");
            user.setPassword(passwordEncoder.encode("123"));
            user.setRoles(List.of("ROLE_USER"));
            userRepository.save(user);
            System.out.println("Test kullanıcısı 'Yazan', 'ROLE_USER' rolüyle birlikte oluşturuldu.");
        }

        // --- YENİ EKLENEN KISIM: Odaları oluştur ---
        if (roomRepository.count() == 0) {
            System.out.println("Veritabanında oda yok, test odaları oluşturuluyor...");

            Room room101 = new Room();
            room101.setRoomNumber("101");
            room101.setType("Tek Kişilik");
            room101.setPricePerNight(new BigDecimal("1500.00"));
            roomRepository.save(room101);

            Room room102 = new Room();
            room102.setRoomNumber("102");
            room102.setType("Tek Kişilik");
            room102.setPricePerNight(new BigDecimal("1600.00"));
            roomRepository.save(room102);

            Room room201 = new Room();
            room201.setRoomNumber("201");
            room201.setType("Çift Kişilik");
            room201.setPricePerNight(new BigDecimal("2500.00"));
            roomRepository.save(room201);

            System.out.println("3 adet test odası oluşturuldu.");
        }
    }
}