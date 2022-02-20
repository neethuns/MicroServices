package com.maveric.customerAcc.demo.feign;

import com.maveric.customerAcc.demo.model.Account;
import com.maveric.customerAcc.demo.model.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
@Component
@FeignClient(name = "account", fallbackFactory = HystrixFallBackFactory.class)
public interface AccountFeign {

    @GetMapping("/AccountCreation/getAccountById/Id/{Id}")
    public ResponseEntity<List<Account>> getAccountByCustomerId(@PathVariable String Id);

   }
