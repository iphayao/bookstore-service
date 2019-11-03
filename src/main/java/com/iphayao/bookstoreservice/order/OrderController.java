package com.iphayao.bookstoreservice.order;

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

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public OrderRespDto createOrder(Principal user, @RequestBody OrderDto orderDto)
            throws AccountNotFoundException {
        return OrderRespDto.builder()
                .price(orderService.createOrder(user.getName(), orderDto))
                .build();
    }
}
