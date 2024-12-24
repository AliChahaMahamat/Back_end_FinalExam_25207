package com.online.banking.Back_End_Banking_System.controller;

import com.online.banking.Back_End_Banking_System.dto.AccountOverviewDTO;
import com.online.banking.Back_End_Banking_System.entity.Account;
import com.online.banking.Back_End_Banking_System.entity.User;
import com.online.banking.Back_End_Banking_System.repository.AccountRepository;
import com.online.banking.Back_End_Banking_System.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/account")
@CrossOrigin(origins = "http://localhost:3000")
public class AccountController {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    // Constructor for Dependency Injection
    public AccountController(AccountRepository accountRepository, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/overview")
    public ResponseEntity<?> getAccountOverview() {
        // Fetch the logged-in user's username from the SecurityContext
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // Fetch the user entity using the username
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        // Fetch accounts associated with the user's ID
        List<Account> accounts = accountRepository.findByUserId(user.getId());

        if (accounts.isEmpty()) {
            return ResponseEntity.status(404).body("No accounts found for the logged-in user.");
        }

        // Map Accounts to AccountOverviewDTO
        List<AccountOverviewDTO> accountDTOs = accounts.stream()
                .map(AccountOverviewDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(accountDTOs);
    }
}
