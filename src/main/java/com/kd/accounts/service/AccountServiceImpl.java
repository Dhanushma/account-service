package com.kd.accounts.service;

import com.kd.accounts.constants.AccountConstant;
import com.kd.accounts.dto.AccountsDTO;
import com.kd.accounts.dto.CustomerDTO;
import com.kd.accounts.entity.Accounts;
import com.kd.accounts.entity.Customer;
import com.kd.accounts.exception.CustomerAlreadyExistsException;
import com.kd.accounts.exception.ResourceNotFoundException;
import com.kd.accounts.mapper.AccountMapper;
import com.kd.accounts.mapper.CustomerMapper;
import com.kd.accounts.repository.AccountRepository;
import com.kd.accounts.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;
    private CustomerRepository customerRepository;

    @Override
    public void createAccount(CustomerDTO customerDTO) {
        Customer customer = CustomerMapper.mapCustomerDTOToCustomer(customerDTO);
        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customerDTO.getMobileNumber());
        if (optionalCustomer.isPresent()) {
            throw new CustomerAlreadyExistsException("Customer already registered with given mobileNumber "
                    + customerDTO.getMobileNumber());
        }
        customer.setCreatedAt(LocalDateTime.now());
        customer.setCreatedBy("Anonymous");
        Customer savedCustomer = customerRepository.save(customer);
        accountRepository.save(createNewAccount(savedCustomer));
    }


    private Accounts createNewAccount(Customer customer) {
        long randomNumber = 1000000000L + new Random().nextInt(900000000);
        Accounts acc = Accounts.builder()
                .customerId(customer.getCustomerId())
                .accountNumber(randomNumber)
                .accountType(AccountConstant.SAVINGS)
                .branchAddress(AccountConstant.ADDRESS)
                .build();
        acc.setCreatedAt(LocalDateTime.now());
        acc.setCreatedBy("Anonymous");
        return acc;
    }

    @Override
    public CustomerDTO fetchAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        Accounts accounts = accountRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString())
        );
        CustomerDTO customerDto = CustomerMapper.mapCustomerToCustomerDto(customer);
        customerDto.setAccountsDTO(AccountMapper.mapAccountToAccountDto(accounts));
        return customerDto;
    }

    @Override
    public boolean updateAccount(CustomerDTO customerDto) {
        boolean isUpdated = false;
        AccountsDTO accountsDto = customerDto.getAccountsDTO();
        if (accountsDto != null) {
            Accounts accounts = accountRepository.findById(accountsDto.getAccountNumber()).orElseThrow(
                    () -> new ResourceNotFoundException("Account", "AccountNumber", accountsDto.getAccountNumber().toString())
            );
            Accounts account = AccountMapper.mapAccountDtoToAccounts(accountsDto);
            account.setAccountNumber(accounts.getAccountNumber());
            account.setCustomerId(accounts.getCustomerId());
            accounts = accountRepository.save(account);

            Long customerId = accounts.getCustomerId();
            Customer customer = customerRepository.findById(customerId).orElseThrow(
                    () -> new ResourceNotFoundException("Customer", "CustomerID", customerId.toString())
            );
            Customer updatedCustomer = CustomerMapper.mapCustomerDTOToCustomer(customerDto);
            updatedCustomer.setCustomerId(customerId);
            customerRepository.save(updatedCustomer);
            isUpdated = true;
        }
        return isUpdated;
    }

    @Override
    public boolean deleteAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        accountRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());
        return true;
    }
}
