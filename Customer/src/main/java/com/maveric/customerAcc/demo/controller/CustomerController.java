package com.maveric.customerAcc.demo.controller;

import com.maveric.customerAcc.demo.model.Customer;
import com.maveric.customerAcc.demo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/CustomerCreation")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @Autowired
    RestTemplate restTemplate;


    @PostMapping("/create")
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        try {
            Integer  customerId= customer.getCustomerId();
            //HttpHeaders header=new HttpHeaders();
            //header.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Integer> httpEntity=new HttpEntity<>(customerId);
            customer.setAccId(restTemplate.postForObject("http://account/AccountCreation/create",httpEntity,Integer.class));
            customer =customerService.createCustomer(customer);
            return new ResponseEntity<>(customer, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/getCustomers")
    public ResponseEntity<List<Customer>> getAccount()
    {
        try{
            List<Customer> accountList = customerService.getCustomers();
            return new ResponseEntity(accountList, HttpStatus.CREATED);

        }
        catch(Exception e)
        {

            return new ResponseEntity(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
  /*  public void createCustomers(@RequestBody Customer customer)
    {
    customerService.createCustomer(customer);
    }
*/

}
