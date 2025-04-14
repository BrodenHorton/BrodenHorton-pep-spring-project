package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.exception.DataIntegrityViolationException;
import com.example.exception.InvalidLoginException;
import com.example.exception.InvalidModelFieldValuesException;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    private AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account registerAccount(Account account) {
        if(account.getUsername().equals("")) {
            throw new InvalidModelFieldValuesException("The account username can not be blank.");
        }
        if(account.getPassword().length() <= 4) {
            throw new InvalidModelFieldValuesException("The account password must be four or more characters long.");
        }
        if(accountRepository.findByUsername(account.getUsername()) != null) {
            throw new DataIntegrityViolationException("Account with username" + account.getUsername() + "already exists.");
        }
        
        return accountRepository.save(account);
    }

    public Account login(Account account) {
        Account loginAccount = accountRepository.findByUsername(account.getUsername());
        if(loginAccount == null || !loginAccount.getPassword().equals(account.getPassword())) {
            throw new InvalidLoginException("Invalid username and password.");
        }

        return loginAccount;
    }
}
