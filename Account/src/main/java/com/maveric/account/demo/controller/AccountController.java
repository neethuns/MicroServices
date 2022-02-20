package com.maveric.account.demo.controller;

import com.maveric.account.demo.exception.AccountNotFoundException;
import com.maveric.account.demo.exception.AccountTypeExistsException;
import com.maveric.account.demo.exception.CustomerNotFoundException;
import com.maveric.account.demo.model.Account;
import com.maveric.account.demo.model.AccountTypes;
import com.maveric.account.demo.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController

@RequestMapping("/AccountCreation")
public class AccountController
{
    @Autowired
    AccountService accountService;

    @PostMapping("/create")
    public Boolean accountCreation(@RequestBody Account account)
    {
        account.setDateTime(LocalDateTime.now());
        account.setIsCustomerActive(Boolean.TRUE);
        account.setAccountTypes(AccountTypes.CASH);
        return accountService.createAccount(account).getIsCustomerActive();

    }
    @PostMapping("/update")
    public Boolean accountUpdate(@RequestBody Account account)
    {
        return accountService.createAccount(account).getIsCustomerActive();

    }

    @PostMapping("/createAccount/{customerId}")
    public ResponseEntity<Account> createAccountNext(@Valid @RequestBody Account account,@PathVariable String customerId)
    {

        if(Boolean.FALSE.equals(accountService.isActive(customerId))) {
            throw new CustomerNotFoundException("Please check the customerId");
        }
        else{
        if(Boolean.TRUE.equals(accountService.isAccountTypeAlreadyExist(customerId,account.getAccountTypes()))) {
            throw new AccountTypeExistsException("Account Type Exist");
        }

        else
        {
            account.setCustomerId(customerId);
            account.setDateTime(LocalDateTime.now());
            account.setIsCustomerActive(Boolean.TRUE);
            return new ResponseEntity<>(accountService.createAccountNext(account), HttpStatus.CREATED);

        } }}
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
    @GetMapping("/getAccountByAccountId/{accountId}")
    public ResponseEntity<Account> getAccountByAccountId(@PathVariable Integer accountId)
    {
        Optional<Account> account=accountService.getAccountById(accountId);
        if(!account.isPresent())
            throw new AccountNotFoundException("Please Check the AccountId");
        else
        return  new ResponseEntity(account.get(),HttpStatus.OK);
    }
    @GetMapping("/getAccountById/Id/{Id}")
    public ResponseEntity<List<Account>> getAccountById(@PathVariable String Id)
    {
        List<Account> account=accountService.getAccountsByCustomerId(Id);
        return  new ResponseEntity(account,HttpStatus.OK);
    }
}
