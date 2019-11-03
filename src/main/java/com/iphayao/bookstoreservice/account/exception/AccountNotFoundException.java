package com.iphayao.bookstoreservice.account.exception;

import com.iphayao.bookstoreservice.common.ApiException;

import static java.lang.String.format;

public class AccountNotFoundException extends ApiException {
    public AccountNotFoundException(String userName) {
        super(format("Account '%s' not found", userName));
    }
}
