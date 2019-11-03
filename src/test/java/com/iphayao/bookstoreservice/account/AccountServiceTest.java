package com.iphayao.bookstoreservice.account;

import com.iphayao.bookstoreservice.account.exception.AccountExistedException;
import com.iphayao.bookstoreservice.account.exception.AccountNotFoundException;
import com.iphayao.bookstoreservice.order.OrderRepository;
import com.iphayao.bookstoreservice.order.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.text.SimpleDateFormat;
import java.util.Optional;

import static com.iphayao.bookstoreservice.TestHelper.mockAccount;
import static com.iphayao.bookstoreservice.TestHelper.mockAccountDto;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private OrderService orderService;
    @Spy
    private AccountMapper accountMapper = new AccountMapperImpl();
    @Spy
    private PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    @InjectMocks
    private AccountService accountService;

    @Test
    void test_create_new_account_expect_account_resp_not_null() throws AccountExistedException {
        // arrange
        AccountDto accountDto = mockAccountDto();
        when(accountRepository.save(any(Account.class))).thenAnswer(e -> e.getArgument(0));

        // act
        AccountRespDto accountRespDto = accountService.createNewAccount(accountDto);

        // assert
        assertNotNull(accountRespDto);
    }

    @Test
    void test_create_new_account_expect_account_resp_with_name_surname_and_birthday() throws AccountExistedException {
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
    void test_create_new_account_expect_save_account_repository() throws AccountExistedException {
        // arrange
        AccountDto accountDto = mockAccountDto();

        // act
        accountService.createNewAccount(accountDto);

        // assert
        verify(accountRepository, times(1)).save(any());
    }

    @Test
    void test_create_new_account_expect_account_exist_exception() {
        AccountDto accountDto = mockAccountDto();
        Optional<Account> mockAccount = mockAccount(accountDto);
        when(accountRepository.findByUsername(eq(accountDto.getUsername()))).thenReturn(mockAccount);

        // act
        // assert
        assertThrows(AccountExistedException.class, () -> accountService.createNewAccount(accountDto));

    }

    @Test
    void test_get_user_by_username_expect_account_detail() throws AccountNotFoundException {
        // arrange
        AccountDto accountDto = mockAccountDto();
        Optional<Account> mockAccount = mockAccount(accountDto);
        String userName = accountDto.getUsername();

        when(accountRepository.findByUsername(eq(userName))).thenReturn(mockAccount);

        // act
        Account account = accountService.getUserByUsername(userName);

        // assert
        assertAll(() -> {
            assertEquals(accountDto.getUsername(), account.getUsername());
            assertEquals(accountDto.getName(), account.getName());
            assertEquals(accountDto.getSurname(), account.getSurname());
            assertEquals(accountDto.getDateOfBirth(), new SimpleDateFormat("dd/MM/yyyy").format(account.getDateOfBirth()));
        });

    }

    @Test
    void test_get_user_by_username_expect_account_not_found_exception_when_account_not_exist() {
        // arrange
        AccountDto accountDto = mockAccountDto();
        String userName = accountDto.getUsername();

        when(accountRepository.findByUsername(eq(userName))).thenReturn(Optional.empty());

        // act
        // assert
        assertThrows(AccountNotFoundException.class, () -> accountService.getUserByUsername(userName));
    }

    @Test
    void test_delete_by_username_expect_delete_account() {
        // arrange
        AccountDto accountDto = mockAccountDto();
        Optional<Account> mockAccount = mockAccount(accountDto);
        String userName = accountDto.getUsername();

        when(accountRepository.findByUsername(eq(userName))).thenReturn(mockAccount);

        // act
        accountService.deleteAccountByUsername(userName);

        // assert
        verify(accountRepository, times(1)).delete(any());
    }



}