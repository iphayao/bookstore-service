package com.iphayao.bookstoreservice.account;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    private AccountRepository accountRepository;
    private AccountMapper accountMapper;
    private PasswordEncoder passwordEncoder;

    public AccountService(AccountRepository accountRepository, AccountMapper accountMapper, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public AccountRespDto createNewAccount(AccountDto accountDto) {
        Account account = accountMapper.accountDtoToAccount(accountDto);
        account.setPassword(passwordEncoder.encode(account.getPassword()));

        account = accountRepository.save(account);

        return accountMapper.accountToAccountRespDto(account);
    }

    public Account getUserByUsername(String name) {
        return accountRepository.findByUsername(name).orElse(null);
    }

    public void deleteAccountByUsername(String name) {
        accountRepository.findByUsername(name)
                .ifPresent(account -> accountRepository.delete(account));
    }
}
