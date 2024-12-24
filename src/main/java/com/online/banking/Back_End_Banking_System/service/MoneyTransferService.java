package com.online.banking.Back_End_Banking_System.service;

import com.online.banking.Back_End_Banking_System.entity.Account;
import com.online.banking.Back_End_Banking_System.entity.Notification;
import com.online.banking.Back_End_Banking_System.entity.Transaction;
import com.online.banking.Back_End_Banking_System.repository.AccountRepository;
import com.online.banking.Back_End_Banking_System.repository.NotificationRepository;
import com.online.banking.Back_End_Banking_System.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class MoneyTransferService {

    private final AccountRepository accountRepository;
    private final NotificationRepository notificationRepository;
    private final TransactionRepository transactionRepository;

    public MoneyTransferService(AccountRepository accountRepository,
                                NotificationRepository notificationRepository,
                                TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.notificationRepository = notificationRepository;
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public void transferMoney(String senderAccountNumber, String receiverAccountNumber, Double amount) {
        // Fetch sender and receiver accounts
        Account sender = accountRepository.findByAccountNumber(senderAccountNumber)
                .orElseThrow(() -> new IllegalArgumentException("Sender account not found"));

        Account receiver = accountRepository.findByAccountNumber(receiverAccountNumber)
                .orElseThrow(() -> new IllegalArgumentException("Receiver account not found"));

        // Check for sufficient balance
        if (sender.getBalance() < amount) {
            throw new IllegalArgumentException("Insufficient balance in sender's account");
        }

        // Perform the transfer
        sender.setBalance(sender.getBalance() - amount);
        receiver.setBalance(receiver.getBalance() + amount);

        accountRepository.save(sender);
        accountRepository.save(receiver);

        // Save the transaction record
        Transaction transaction = new Transaction();
        transaction.setSenderAccountNumber(senderAccountNumber);
        transaction.setReceiverAccountNumber(receiverAccountNumber);
        transaction.setAmount(amount);
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setUser(sender.getUser()); // Associate the transaction with the sender
        transactionRepository.save(transaction);

        // Notify the receiver
        String message = "You have received $" + amount + " from account " + senderAccountNumber;
        Notification notification = new Notification(message, LocalDateTime.now(), receiver.getUser());
        notificationRepository.save(notification);
    }
}
