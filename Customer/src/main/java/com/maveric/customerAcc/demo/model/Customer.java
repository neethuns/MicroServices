package com.maveric.customerAcc.demo.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class Customer {


    private int customerId;
    private String name;
    private long phoneNumber;
    private Integer AccId;
}
