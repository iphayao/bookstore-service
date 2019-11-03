package com.iphayao.bookstoreservice.account;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    @Mapping(target = "dateOfBirth", dateFormat = "dd/MM/yyyy")
    Account accountDtoToAccount(AccountDto dto);

    @Mapping(target = "dateOfBirth", dateFormat = "dd/MM/yyyy")
    AccountRespDto accountToAccountRespDto(Account account);
}
