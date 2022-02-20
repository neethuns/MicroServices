package com.maveric.customerAcc.demo.controller;
import com.maveric.customerAcc.demo.exception.CustomerNotFoundException;
import com.maveric.customerAcc.demo.feign.AccountFeign;
import com.maveric.customerAcc.demo.model.Account;
import com.maveric.customerAcc.demo.model.Customer;
import com.maveric.customerAcc.demo.model.CustomerAccountList;
import com.maveric.customerAcc.demo.model.CustomerAccountResponse;
import com.maveric.customerAcc.demo.repo.CustomerRepo;
import com.maveric.customerAcc.demo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/CustomerCreation")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @Autowired
    CustomerRepo customerRepo;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    AccountFeign accountFeign;

    @PostMapping("/create")
    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody Customer customer) {

        Account account = new Account();
        Customer customer1 = customerService.createCustomer(customer);
        account.setCustomerId(customer1.getId());
        HttpEntity<Account> httpEntity = new HttpEntity<>(account);
        customer1.setIsActive(restTemplate.postForObject("http://account/AccountCreation/create", httpEntity, Boolean.class));
        return new ResponseEntity<>(customerService.createCustomer(customer1), HttpStatus.CREATED);

    }

    @GetMapping("/getCustomerAccounts/{Id}")
    public ResponseEntity<CustomerAccountList> getAccountByCustomerId(@PathVariable String Id) {
        Optional<Customer> customer = customerService.getById(Id);
        CustomerAccountList customerAccountResponse = new CustomerAccountList();
        ResponseEntity<List<Account>> account = accountFeign.getAccountByCustomerId(Id);
        customerAccountResponse.setCustomer(customer.get());
        customerAccountResponse.setAccount(account.getBody());
        return new ResponseEntity(customerAccountResponse, HttpStatus.CREATED);
    }

    @GetMapping("/getCustomers")
    public ResponseEntity<List<Customer>> getCustomer() {
        try {
            List<Customer> customerList = customerService.getCustomers();
            return new ResponseEntity(customerList, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getCustomers/Id/{Id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable("Id") String Id) {
        Customer customer = customerService.getCustomerById(Id);

        return new ResponseEntity(customer, HttpStatus.CREATED);
    }

    @PutMapping("/updateCustomer/{Id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable String Id, @RequestBody Customer customer) {

        return new ResponseEntity(customerService.UpdateById(Id, customer), HttpStatus.OK);

    }


    @DeleteMapping("/deleteCustomer/{Id}")
    public String deleteCustomer(@PathVariable String Id) {
        ResponseEntity<List<Account>> account=accountFeign.getAccountByCustomerId(Id);
       return customerService.deleteById(Id,account.getBody());

    }
}