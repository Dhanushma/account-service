package com.kd.accounts.service;

import com.kd.accounts.dto.CustomerDTO;

public interface AccountService {
    void createAccount(CustomerDTO customerDTO);

    CustomerDTO fetchAccount(String mobileNumber);

    boolean updateAccount(CustomerDTO customerDto);

    boolean deleteAccount(String mobileNumber);
}
