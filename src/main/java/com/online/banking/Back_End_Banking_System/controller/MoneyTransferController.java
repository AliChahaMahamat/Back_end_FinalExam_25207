package com.online.banking.Back_End_Banking_System.controller;

import com.online.banking.Back_End_Banking_System.service.MoneyTransferService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transfer")
@CrossOrigin(origins = "http://localhost:3000")
public class MoneyTransferController {

    private final MoneyTransferService moneyTransferService;

    public MoneyTransferController(MoneyTransferService moneyTransferService) {
        this.moneyTransferService = moneyTransferService;
    }

    @PostMapping
    public ResponseEntity<?> transferMoney(
            @RequestParam String senderAccountNumber,
            @RequestParam String receiverAccountNumber,
            @RequestParam Double amount) {
        try {
            moneyTransferService.transferMoney(senderAccountNumber, receiverAccountNumber, amount);
            return ResponseEntity.ok("Money transferred successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
