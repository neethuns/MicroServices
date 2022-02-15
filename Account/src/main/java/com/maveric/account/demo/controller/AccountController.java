package com.maveric.account.demo.controller;

import com.maveric.account.demo.model.Account;
import com.maveric.account.demo.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController

@RequestMapping("/AccountCreation")
public class AccountController
{
    @Autowired
    AccountService accountService;
    @PostMapping("/create")
    public Integer accountCreation(@Valid @RequestBody Integer customerId)
    {
        Account account=new Account();
        account.setCustomerId(customerId);

             return accountService.createAccount(account).getAccountId();

    }
    @GetMapping("/getAccounts")
    public ResponseEntity<List<Account>> getAccount()
    {
        try{
            List<Account> accountList = accountService.getAccounts();
            return new ResponseEntity(accountList, HttpStatus.CREATED);

        }
        catch(Exception e)
        {

            return new ResponseEntity(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
