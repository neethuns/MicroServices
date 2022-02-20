package com.maveric.customerAcc.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerAccountList {
    Customer customer;
    List<Account> account;
}
