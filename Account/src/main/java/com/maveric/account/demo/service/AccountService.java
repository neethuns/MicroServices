package com.maveric.account.demo.service;

import com.maveric.account.demo.exception.CustomerNotActiveException;
import com.maveric.account.demo.exception.CustomerNotFoundException;
import com.maveric.account.demo.model.Account;
import com.maveric.account.demo.model.AccountTypes;
import com.maveric.account.demo.repo.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Optional;

@Service
public class AccountService {
    List<Account> accountList = new ArrayList<>();
    Account account;
    @Autowired
    AccountRepo accountRepo;

    public Account createAccount(Account account) {
        return accountRepo.save(account);
    }

    public List<Account> getAccounts() {
        accountList = accountRepo.findAll();
        return accountList;
    }

    public Optional<Account> getAccountById(Integer accountId) {
        Optional<Account> account = accountRepo.findByAccountId(accountId);
        return account;
    }

    public Account createAccountNext(Account account) {

        return accountRepo.save(account);
    }

    /*public Optional<List<Account>> getAccountByCustomerId(String customerId) {
        return accountRepo.findByCustomerId(customerId);

    }*/
    public List<Account> getAccountsByCustomerId(String customerId) {
        return accountRepo.findAllByCustomerId(customerId);

    }

    public Boolean getAccountByCustomerId(String customerId) {
        List<Account> accounts = accountRepo.findByCustomerId(customerId);
        //below if checks whether the id passed is valid
        if (accounts.isEmpty()) {
            throw new CustomerNotFoundException("Please check the Customer ID");
        }
        for (Account account : accounts) {
            //below if checks whether any account is active for this customer
            if (account.getIsCustomerActive().equals(Boolean.FALSE))
                throw new CustomerNotActiveException("Customer is Not Active");

        }
        return true;
    }

    public Boolean isActive(String customerId) {

            List<Account> accounts = new ArrayList<>(accountRepo.findByCustomerId(customerId));
            //below if checks whether the id passed is valid
            if(accounts.isEmpty()){
                return false;
            }
            for(Account account : accounts)
            {
                //below if checks whether any account is active for this customer
                if(Boolean.TRUE.equals(account.getIsCustomerActive()))
                    return true;
            }
            return false;
        }

    public Boolean isAccountTypeAlreadyExist(String customerId, AccountTypes accountTypes) {

        List<Account> accountTypeList = new ArrayList<>(accountRepo.findByCustomerId(customerId));
        for(Account account : accountTypeList){
            if(account.getAccountTypes()==accountTypes)
                return true;
        }
        return false;


    }
}