package com.kd.accounts;

import com.kd.accounts.dto.ContactsDTO;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Account Microservice Open API Doc",
				description = "Demo for Accounts microservice REST API Documentation",
				version = "v1"
		)
)
@EnableConfigurationProperties(ContactsDTO.class)
public class AccountsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountsApplication.class, args);
	}

}
