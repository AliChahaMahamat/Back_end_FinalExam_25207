package com.online.banking.Back_End_Banking_System.controller;

import com.online.banking.Back_End_Banking_System.entity.Transaction;
import com.online.banking.Back_End_Banking_System.repository.TransactionRepository;
import com.online.banking.Back_End_Banking_System.entity.User;
import com.online.banking.Back_End_Banking_System.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    public TransactionController(TransactionRepository transactionRepository, UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    // Endpoint to get the transaction history for the authenticated user
    @GetMapping("/history")
    public ResponseEntity<?> getTransactionHistory() {
        // Extract the currently authenticated user (from the token)
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername(); // Username can be used to identify the user

        // Fetch the user by username (You can modify this to match your UserRepository implementation)
        // Assuming that the username is unique (it could be email or user id)
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Get the account number from the authenticated user
        String accountNumber = user.getAccounts().get(0).getAccountNumber();

        // Retrieve transactions by sender or receiver account number
        List<Transaction> transactions = transactionRepository
                .findBySenderAccountNumberOrReceiverAccountNumber(accountNumber, accountNumber);

        // Check if no transactions were found and return appropriate message
        if (transactions.isEmpty()) {
            return ResponseEntity.status(404).body("No transactions found for this account.");
        }

        // Return the list of transactions with a 200 OK status
        return ResponseEntity.ok(transactions);
    }
}
