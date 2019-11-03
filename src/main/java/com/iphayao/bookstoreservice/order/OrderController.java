package com.iphayao.bookstoreservice.order;

import com.iphayao.bookstoreservice.account.Account;
import com.iphayao.bookstoreservice.account.AccountService;
import com.iphayao.bookstoreservice.account.exception.AccountNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/users/orders")
public class OrderController {
    private OrderService orderService;
    private AccountService accountService;

    public OrderController(OrderService orderService, AccountService accountService) {
        this.orderService = orderService;
        this.accountService = accountService;
    }

    @PostMapping
    public OrderRespDto createOrder(Principal user, @RequestBody OrderDto orderDto)
            throws AccountNotFoundException {
        Account account = accountService.getUserByUsername(user.getName());
        return OrderRespDto.builder()
                .price(orderService.createOrder(user.getName(), orderDto, account))
                .build();
    }
}
