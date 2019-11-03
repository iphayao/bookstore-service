package com.iphayao.bookstoreservice.account;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class AccountDto {
    private String username;
    private String password;
    private String name;
    private String surname;
    private String dateOfBirth;
}
