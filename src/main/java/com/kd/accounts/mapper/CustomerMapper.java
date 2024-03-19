package com.kd.accounts.mapper;

import com.kd.accounts.dto.CustomerDTO;
import com.kd.accounts.entity.Customer;

public class CustomerMapper {

    public static Customer mapCustomerDTOToCustomer(CustomerDTO customerDTO){
        return Customer.builder()
                .name(customerDTO.getName())
                .email(customerDTO.getEmail())
                .mobileNumber(customerDTO.getMobileNumber())
                .build();
    }

    public static CustomerDTO mapCustomerToCustomerDto(Customer customer){
        return CustomerDTO.builder()
                .name(customer.getName())
                .email(customer.getEmail())
                .mobileNumber(customer.getMobileNumber())
                .build();
    }
}
