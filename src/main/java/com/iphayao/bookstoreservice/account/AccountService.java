package com.iphayao.bookstoreservice.account;

import com.iphayao.bookstoreservice.account.exception.AccountNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    public AccountRespDto createNewAccount(AccountDto accountDto) throws AccountExistedException {
        Optional<Account> existAccount = accountRepository.findByUsername(accountDto.getUsername());
        if(existAccount.isPresent()) {
            throw new AccountExistedException(accountDto.getUsername());
        }

        Account account = accountMapper.accountDtoToAccount(accountDto);
        account.setPassword(passwordEncoder.encode(account.getPassword()));

        account = accountRepository.save(account);

        return accountMapper.accountToAccountRespDto(account);
    }

    public Account getUserByUsername(String name) throws AccountNotFoundException {
        return accountRepository.findByUsername(name).orElseThrow(() -> new AccountNotFoundException(name));
    }

    public void deleteAccountByUsername(String name) {
        accountRepository.findByUsername(name)
                .ifPresent(account -> accountRepository.delete(account));
    }
}
