package com.iphayao.bookstoreservice.account;

import com.iphayao.bookstoreservice.account.exception.AccountExistedException;
import com.iphayao.bookstoreservice.account.exception.AccountNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Slf4j
@RestController
@RequestMapping("/users")
public class AccountController {
    private AccountService accountService;
    private AccountMapper accountMapper;

    public AccountController(AccountService accountService, AccountMapper accountMapper) {
        this.accountService = accountService;
        this.accountMapper = accountMapper;
    }

    @GetMapping
    public AccountRespDto getUser(Principal user) throws AccountNotFoundException {
        Account account = accountService.getUserByUsername(user.getName());
        return accountMapper.accountToAccountRespDto(account);
    }

    @PostMapping
    public void createUser(@RequestBody AccountDto accountDto) throws AccountExistedException {
        accountService.createNewAccount(accountDto);
    }

    @DeleteMapping
    public void deleteUser(Principal user) {
        accountService.deleteAccountByUsername(user.getName());
    }

}
