package com.maveric.account.demo.service;

import com.maveric.account.demo.model.Account;
import com.maveric.account.demo.repo.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountService {
List<Account> accountList=new ArrayList<>();
    @Autowired
    AccountRepo accountRepo;

    public Account createAccount(Account account)
    {
        return accountRepo.save(new Account(account.getAccountId(),account.getCustomerId()));
    }
public List<Account> getAccounts()
{
    accountList=accountRepo.findAll();
    return accountList;
}
}
