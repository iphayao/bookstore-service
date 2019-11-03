package com.iphayao.bookstoreservice.account;

import com.iphayao.bookstoreservice.order.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    @Mapping(target = "dateOfBirth", dateFormat = "dd/MM/yyyy")
    Account accountDtoToAccount(AccountDto dto);

    @Mapping(target = "books", source = "orders", qualifiedByName = "")
    @Mapping(target = "dateOfBirth", dateFormat = "dd/MM/yyyy")
    AccountRespDto accountToAccountRespDto(Account account);

    default List<Integer> ordersToBooks(List<Order> orders) {
        if(orders != null) {
            return orders.stream().map(Order::getBookId)
                    .collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }
}
