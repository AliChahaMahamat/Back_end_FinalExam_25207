package com.online.banking.Back_End_Banking_System.controller;

import com.online.banking.Back_End_Banking_System.entity.Notification;
import com.online.banking.Back_End_Banking_System.entity.User;
import com.online.banking.Back_End_Banking_System.repository.NotificationRepository;
import com.online.banking.Back_End_Banking_System.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin(origins = "http://localhost:3000")
public class NotificationController {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    public NotificationController(NotificationRepository notificationRepository, UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<?> getNotifications() {
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            List<Notification> notifications = notificationRepository.findByUserId(user.getId());
            return ResponseEntity.ok(notifications);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to fetch notifications.");
        }
    }

}
