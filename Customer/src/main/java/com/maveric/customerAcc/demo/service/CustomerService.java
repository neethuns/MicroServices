package com.maveric.customerAcc.demo.service;

import com.maveric.customerAcc.demo.controller.CustomerController;
import com.maveric.customerAcc.demo.exception.CustomerNotFoundException;
import com.maveric.customerAcc.demo.feign.AccountFeign;
import com.maveric.customerAcc.demo.model.Account;
import com.maveric.customerAcc.demo.model.Address;
import com.maveric.customerAcc.demo.model.Customer;
import com.maveric.customerAcc.demo.model.CustomerAccountResponse;
import com.maveric.customerAcc.demo.repo.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    CustomerRepo customerRepo;
    @Autowired
    AccountFeign accountFeign;
    @Autowired
    RestTemplate restTemplate;

    public Customer createCustomer(Customer customer)
    {
        return customerRepo.save(customer);
    }

    public List<Customer> getCustomers() {
        return customerRepo.findAll();
    }

    public Optional<Customer> getById(String Id) {
        return customerRepo.findById(Id);
    }

    public Customer getCustomerById(String Id) {
      Optional<Customer> customer= customerRepo.findById(Id);

      if(customer.isPresent())
           return customer.get();
      else
        throw new CustomerNotFoundException("Please check Id");
    }
    public Customer UpdateById(String Id, Customer customer){
        Optional<Customer> customer1=customerRepo.findById(Id);

             if(customer1.isPresent()){
            Customer customerData=customer1.get();
           customerData.setPhoneNumber(customer.getPhoneNumber());
           customerData.setAddress(customer.getAddress());
           customerData.setEmail(customer.getEmail());
            return customerRepo.save(customerData);

        }
        else
            throw new CustomerNotFoundException("Please check the Id");

    }


    public String deleteById(String Id,List<Account> account) {
        Optional<Customer> customer = customerRepo.findById(Id);
        if (customer.isPresent()) {
            Customer customer1 = customer.get();
            customer1.setIsActive(Boolean.FALSE);
            customerRepo.save(customer1);

            // CustomerAccountResponse customerAccountResponse=new CustomerAccountResponse();
            for (Account account1 : account) {

                account1.setIsCustomerActive(Boolean.FALSE);
                HttpEntity<Account> httpEntity = new HttpEntity(account1);
                customer1.setIsActive(restTemplate.postForObject("http://account/AccountCreation/update", httpEntity, Boolean.class));


            }
        } else throw new CustomerNotFoundException("Please check the CustomerId");
        return "Customer Deleted Successfully";
    }

    }

