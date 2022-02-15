package com.maveric.customerAcc.demo.service;

import com.maveric.customerAcc.demo.model.Customer;
import com.maveric.customerAcc.demo.repo.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class CustomerService {
    @Autowired
    CustomerRepo customerRepo;


    public Customer createCustomer(Customer customer)
    {

        return customerRepo.save(new Customer(customer.getCustomerId(),customer.getName(),customer.getPhoneNumber(),customer.getAccId()));
    }

    public List<Customer> getCustomers() {
        return customerRepo.findAll();
    }
}
