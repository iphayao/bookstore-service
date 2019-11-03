package com.iphayao.bookstoreservice;

import com.iphayao.bookstoreservice.account.Account;
import com.iphayao.bookstoreservice.account.AccountDto;
import com.iphayao.bookstoreservice.account.AccountMapper;
import com.iphayao.bookstoreservice.account.AccountMapperImpl;

import java.util.Optional;

public class TestHelper {
    private static AccountMapper accountMapper = new AccountMapperImpl();

    public static Optional<Account> mockAccount(AccountDto accountDto) {
        return Optional.ofNullable(accountMapper.accountDtoToAccount(accountDto));
    }

    public static Optional<Account> mockAccount() {
        Account account = accountMapper.accountDtoToAccount(mockAccountDto());
        account.setId(1);
        return Optional.ofNullable(account);
    }

    public static AccountDto mockAccountDto() {
        return AccountDto.builder()
                .username("john.doe")
                .password("thisismysecret")
                .name("John")
                .surname("Doe")
                .dateOfBirth("15/01/1985")
                .build();
    }
}
