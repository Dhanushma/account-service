package com.kd.accounts.mapper;

import com.kd.accounts.dto.AccountsDTO;
import com.kd.accounts.entity.Accounts;

public class AccountMapper {

    public static Accounts mapAccountDtoToAccounts(AccountsDTO accountsDTO){
        return Accounts.builder()
                .accountNumber(accountsDTO.getAccountNumber())
                .accountType(accountsDTO.getAccountType())
                .branchAddress(accountsDTO.getBranchAddress())
                .build();
    }

    public static AccountsDTO mapAccountToAccountDto(Accounts account){
        return AccountsDTO.builder()
                .accountNumber(account.getAccountNumber())
                .accountType(account.getAccountType())
                .branchAddress(account.getBranchAddress())
                .build();
    }
}
