package com.maveric.customerAcc.demo.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class CustomerAccountResponse {

private Customer customer;

private Account account;
}
