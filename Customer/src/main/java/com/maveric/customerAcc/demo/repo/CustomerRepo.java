package com.maveric.customerAcc.demo.repo;

import com.maveric.customerAcc.demo.model.Customer;
import org.springframework.data.mongodb.core.MongoAdminOperations;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepo extends MongoRepository<Customer,String> {


}
