package com.iphayao.bookstoreservice;

import com.iphayao.bookstoreservice.account.Account;
import com.iphayao.bookstoreservice.account.AccountDto;
import com.iphayao.bookstoreservice.account.AccountMapper;
import com.iphayao.bookstoreservice.account.AccountMapperImpl;
import com.iphayao.bookstoreservice.book.Book;
import com.iphayao.bookstoreservice.order.OrderDto;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class TestHelper {
    private static AccountMapper accountMapper = new AccountMapperImpl();
    private static PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    public static Optional<Account> mockAccount(AccountDto accountDto) {
        return Optional.ofNullable(accountMapper.accountDtoToAccount(accountDto));
    }

    public static Optional<Account> mockAccount() {
        Account account = accountMapper.accountDtoToAccount(mockAccountDto());
        account.setId(1);
        return Optional.ofNullable(account);
    }

    public static Optional<Account> mockAccount(Map<String, String> values) {
        Account account = new Account();
        account.setUsername(values.get("username"));
        account.setPassword(passwordEncoder.encode(values.get("password")));

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

    public static OrderDto mockOrderDto() {
        return OrderDto.builder()
                .orders(Arrays.asList(1, 4))
                .build();
    }

    public static List<Book> mockRemoteBook() {
        return Arrays.asList(
                Book.builder()
                        .id(1)
                        .bookName("test book name")
                        .authorName("john doe")
                        .price(100.0)
                        .build(),
                Book.builder()
                        .id(2)
                        .bookName("test book name")
                        .authorName("john doe")
                        .price(100.0)
                        .build()
        );
    }
}
