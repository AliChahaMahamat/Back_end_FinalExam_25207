package com.online.banking.Back_End_Banking_System.service;

import com.online.banking.Back_End_Banking_System.entity.Account;
import com.online.banking.Back_End_Banking_System.entity.User;
import com.online.banking.Back_End_Banking_System.repository.AccountRepository;
import com.online.banking.Back_End_Banking_System.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    public AccountService(AccountRepository accountRepository, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    public List<Account> getAccountsByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return accountRepository.findByUserId(user.getId());
    }
}
