package com.iphayao.bookstoreservice.account.exception;

import com.iphayao.bookstoreservice.common.ApiException;

import static java.lang.String.format;

public class AccountExistedException extends ApiException {
    public AccountExistedException(String userName) {
        super(format("Account of username '%s' already exist", userName));
    }
}
