package com.maveric.customerAcc.demo.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
public class Account {
    private Integer accountId;
    private String customerId;
    private Boolean isCustomerActive;

    private AccountTypes accountTypes;
    LocalDateTime dateTime;


}
