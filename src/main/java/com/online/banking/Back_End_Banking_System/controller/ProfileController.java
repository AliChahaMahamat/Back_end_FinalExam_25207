package com.online.banking.Back_End_Banking_System.controller;

import com.online.banking.Back_End_Banking_System.entity.User;
import com.online.banking.Back_End_Banking_System.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    private final UserRepository userRepository;

    public ProfileController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<?> getUserProfile(Authentication authentication) {
        String username = authentication.getName();
        Optional<User> userOpt = userRepository.findByUsername(username);

        if (userOpt.isEmpty()) {
            return ResponseEntity.status(404).body("User not found");
        }

        User user = userOpt.get();
        String profilePictureUrl = user.getProfilePicture() != null
                ? "http://localhost:8083/" + user.getProfilePicture().replace("\\", "/")
                : null;

        // Return UserProfileDto with profile picture URL
        return ResponseEntity.ok(new UserProfileDto(
                user.getUsername(),
                user.getEmail(),
                user.getFullName(),
                user.getPhoneNumber(),
                profilePictureUrl
        ));
    }

    @PutMapping
    public ResponseEntity<?> updateUserProfile(Authentication authentication,
                                               @RequestBody User updatedUser) {
        String username = authentication.getName();
        Optional<User> userOpt = userRepository.findByUsername(username);

        if (userOpt.isEmpty()) {
            return ResponseEntity.status(404).body("User not found");
        }

        User user = userOpt.get();
        user.setFullName(updatedUser.getFullName());
        user.setPhoneNumber(updatedUser.getPhoneNumber());
        userRepository.save(user);

        return ResponseEntity.ok("Profile updated successfully");
    }

    @PostMapping("/upload-picture")
    public ResponseEntity<?> uploadProfilePicture(Authentication authentication, @RequestParam("file") MultipartFile file) {
        String username = authentication.getName();
        Optional<User> userOpt = userRepository.findByUsername(username);

        if (userOpt.isEmpty()) {
            return ResponseEntity.status(404).body("User not found");
        }

        User user = userOpt.get();

        try {
            String uploadDir = "uploads/profile-pictures/";
            File directory = new File(uploadDir);

            if (!directory.exists()) {
                directory.mkdirs();
            }

            String originalFilename = file.getOriginalFilename();
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String uniqueFilename = username + "_" + System.currentTimeMillis() + fileExtension;

            Path filePath = Paths.get(uploadDir + uniqueFilename);
            Files.write(filePath, file.getBytes());

            user.setProfilePicture(uploadDir + uniqueFilename);
            userRepository.save(user);

            System.out.println("File saved at: " + filePath.toString()); // Debugging step

            return ResponseEntity.ok("Profile picture updated successfully");

        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error saving profile picture: " + e.getMessage());
        }
    }



    public static class UserProfileDto {
        private final String username;
        private final String email;
        private final String fullName;
        private final String phoneNumber;
        private final String profilePicture;

        public UserProfileDto(String username, String email, String fullName, String phoneNumber, String profilePicture) {
            this.username = username;
            this.email = email;
            this.fullName = fullName;
            this.phoneNumber = phoneNumber;
            this.profilePicture = profilePicture;
        }

        public String getUsername() {
            return username;
        }

        public String getEmail() {
            return email;
        }

        public String getFullName() {
            return fullName;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public String getProfilePicture() {
            return profilePicture;
        }
    }
}
