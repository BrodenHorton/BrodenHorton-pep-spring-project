package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Account;

/**
 * AccountRepository is a repository class that handles operations in the DAO layer for
 * the account table.
 */
public interface AccountRepository extends JpaRepository<Account, Long> {
    /**
     * Property expression for finding all accounts with a given accountId value.
     * @param  id The accountId value that is being searched for.
     * @return Returns the Account that has the given id.
     */
    Account findByAccountId(int id);

    /**
     * Property expression for finding all users with a given username.
     * @param  username The username string that is being searched for.
     * @return Returns the account with the given username.
     */
    Account findByUsername(String username);
}
