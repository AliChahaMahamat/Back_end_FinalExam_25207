package com.online.banking.Back_End_Banking_System.entity;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String senderAccountNumber;
    private String receiverAccountNumber;
    private Double amount;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = true) // Nullable to allow flexibility
    private User user; // Add this only if User is a necessary field

    private LocalDateTime transactionDate;

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getSenderAccountNumber() { return senderAccountNumber; }
    public void setSenderAccountNumber(String senderAccountNumber) { this.senderAccountNumber = senderAccountNumber; }

    public String getReceiverAccountNumber() { return receiverAccountNumber; }
    public void setReceiverAccountNumber(String receiverAccountNumber) { this.receiverAccountNumber = receiverAccountNumber; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public LocalDateTime getTransactionDate() { return transactionDate; }
    public void setTransactionDate(LocalDateTime transactionDate) { this.transactionDate = transactionDate; }
}
