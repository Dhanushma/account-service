package com.kd.accounts.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class AccountsDTO {
    private Long accountNumber;

    private String accountType;

    private String branchAddress;
}
