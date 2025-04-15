package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.exception.DataIntegrityViolationException;
import com.example.exception.InvalidLoginException;
import com.example.exception.InvalidModelFieldValuesException;
import com.example.repository.AccountRepository;

/**
 * AccountService is a service class that holds the business logic for working with Account objects.
 * @author Broden Horton
 */
@Service
public class AccountService {
    /**
     * Repository class for interacting with Accounts in the DAO layer.
     */
    private AccountRepository accountRepository;

    /**
     * Parameterized constructor annotated with @Autowired so Spring injects the AccountRepository dependency.
     * @param accountRepository
     */
    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    /**
     * Registers a new account into the database.
     * @param account The account to be registered into the database.
     * @throws InvalidModelFieldValuesException when the account being registered does not have a username or
     *         when the account's password is less than four characters long.
     * @throws DataIntegrityViolationException when the account being registered is already in the database.
     * @return Returns the Account that was registered to the database.
     */
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

    /**
     * Returns an account based on the login credentials.
     * @param account The credentials used to try and log into an account.
     * @throws InvalidLoginException when the login credentials are invalid.
     * @return Returns the account that has been successfully logged into.
     */
    public Account login(Account account) {
        Account loginAccount = accountRepository.findByUsername(account.getUsername());
        if(loginAccount == null || !loginAccount.getPassword().equals(account.getPassword())) {
            throw new InvalidLoginException("Invalid username and password.");
        }

        return loginAccount;
    }
}
