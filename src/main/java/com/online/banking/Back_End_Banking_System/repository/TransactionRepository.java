package com.online.banking.Back_End_Banking_System.repository;

import com.online.banking.Back_End_Banking_System.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findBySenderAccountNumberOrReceiverAccountNumber(String senderAccountNumber, String receiverAccountNumber);
}
