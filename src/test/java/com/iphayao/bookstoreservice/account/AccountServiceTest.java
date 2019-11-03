package com.iphayao.bookstoreservice.account;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {
    @Mock
    private AccountRepository accountRepository;
    @Spy
    private AccountMapper accountMapper = new AccountMapperImpl();

    @InjectMocks
    private AccountService accountService;

    @Test
    void test_create_new_account_expect_account_resp_not_null() {
        // arrange
        AccountDto accountDto = mockAccountDto();
        when(accountRepository.save(any(Account.class))).thenAnswer(e -> e.getArgument(0));

        // act
        AccountRespDto accountRespDto = accountService.createNewAccount(accountDto);

        // assert
        assertNotNull(accountRespDto);
    }

    @Test
    void test_create_new_account_expect_account_resp_with_name_surname_and_birthday() {
        // arrange
        AccountDto accountDto = mockAccountDto();
        when(accountRepository.save(any(Account.class))).thenAnswer(e -> e.getArgument(0));

        // act
        AccountRespDto accountRespDto = accountService.createNewAccount(accountDto);

        // assert
        assertAll(() -> {
            assertEquals(accountDto.getName(), accountRespDto.getName());
            assertEquals(accountDto.getSurname(), accountRespDto.getSurname());
            assertEquals(accountDto.getDateOfBirth(), accountRespDto.getDateOfBirth());
        });
    }

    @Test
    void test_create_new_account_expect_save_account_repository() {
        // arrange
        AccountDto accountDto = mockAccountDto();

        // act
        accountService.createNewAccount(accountDto);

        // assert
        verify(accountRepository, times(1)).save(any());
    }



    private AccountDto mockAccountDto() {
        return AccountDto.builder()
                .username("john.doe")
                .password("thisismysecret")
                .dateOfBirth("15/01/1985")
                .build();
    }

}