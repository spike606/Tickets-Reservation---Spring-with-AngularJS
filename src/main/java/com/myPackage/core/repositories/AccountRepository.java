package com.myPackage.core.repositories;

import java.util.List;

import com.myPackage.core.entities.Account;

public interface AccountRepository {
    public Account findAccount(Long id);
    public Account findAccountByLogin(String login);
    public List<Account> findAllAccounts();
    public Account createAccount(Account data);
    public Account deleteAccount(Long id);


}
