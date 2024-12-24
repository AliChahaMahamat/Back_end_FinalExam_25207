package com.online.banking.Back_End_Banking_System.dto;


import com.online.banking.Back_End_Banking_System.entity.Account;

public class AccountOverviewDTO {
    private Long id;
    private String accountType;
    private String accountNumber;
    private Double balance;

    // Default Constructor
    public AccountOverviewDTO() {}

    // Constructor to initialize fields from Account entity
    public AccountOverviewDTO(Account account) {
        this.id = account.getId();
        this.accountType = account.getAccountType();
        this.accountNumber = account.getAccountNumber();
        this.balance = account.getBalance();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
