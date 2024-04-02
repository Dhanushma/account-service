package com.kd.accounts.dto;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties(prefix = "accounts")
public record ContactsDTO(String message, Map<String,String> contactDetails) {
}
